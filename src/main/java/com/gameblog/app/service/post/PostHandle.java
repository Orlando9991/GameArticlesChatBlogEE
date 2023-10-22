/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.post;

import com.gameblog.app.repository.PostRepository;
import com.gameblog.app.model.Post;
import com.gameblog.app.model.User;
import com.gameblog.app.service.session.SessionHandle;
import com.gameblog.app.repository.UserRepository;
import com.gameblog.app.tools.GeneralViewTools;
import com.gameblog.app.tools.GraphCreator;
import com.gameblog.app.tools.ImageByteConverter;
import com.gameblog.app.utils.EMonth;
import com.gameblog.app.utils.EPostEvent;
import com.gameblog.app.utils.EPostTab;
import com.gameblog.app.utils.RepositoryException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.transaction.Transactional;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author orlan
 */
@Named("PostHandle")
@Stateful   //To handle timers of the NewsHandler
@SessionScoped //Fire CDI events and for JSF
public class PostHandle implements Serializable {

    @Inject
    PostRepository postRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    SessionHandle sessionHandle;

    @Inject
    GeneralViewTools generalBeanTools;
    
    @Inject
    Event<EPostEvent> newsEvent;
    
 
    private UploadedFile imgFile;

    private Post post;
    
    private Post viewPost;

    private EPostTab currentPostTab;

    private LineChartModel PostNumberGraph;

    private DonutChartModel CategoryPostNumberGraph;

    private static final Logger logger = Logger.getLogger(PostHandle.class.getName());

    
    
    @PostConstruct
    public void init() {
        setNewPost();
        currentPostTab = EPostTab.ALL;
    }

    public PostHandle() {
    }

    public UploadedFile getImgFile() {
        return imgFile;
    }

    public void setImgFile(UploadedFile imgFile) throws IOException {
        this.imgFile = imgFile;
        this.post.setImage(ImageByteConverter.getByteOfImage(imgFile));
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post getViewPost() {
        return viewPost;
    }

    public void setViewPost(Post viewPost) {
        this.viewPost = viewPost;
    }

    private void setNewPost() {
        post = new Post();
    }

    public EPostTab getCurrentPostTab() {
        return currentPostTab;
    }

    public void setCurrentPostTab(EPostTab currentPostTab) {
        this.currentPostTab = currentPostTab;
    }

    
   

    public LineChartModel getPostNumberGraph() {
        PostNumberGraph = createNumberOfPostGraph();
        return PostNumberGraph;
    }

    public DonutChartModel getCategoryPostNumberGraph() {
        CategoryPostNumberGraph = createCategoryNumberGraph();
        return CategoryPostNumberGraph;
    }

    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
    public void createPost() throws ServletException, IOException {
        try {
            User user = (User) (userRepository.findByName(sessionHandle.getUserName()).orElseGet(null));
            post.setUser(user);
            post.setDate(new Date());
            postRepository.create(post);
            generalBeanTools.executePrimeFacesScript("PF('postCreatorDlg').hide();");
            newsEvent.fire(EPostEvent.CREATE);
            generalBeanTools.showAlertMessage("Sucess", "Post Created", FacesMessage.SEVERITY_INFO);
            generalBeanTools.executePrimeFacesUpdate(":articles-form:category-tab");
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }


    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
    public List<Post> getAllPostsList() {
        List<Post> resulList = null;
        try {
            resulList = postRepository.findAll();
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return resulList;
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
    public List<Post> getAllPostsListByDate(Date date) {
       List<Post> resulList = null;
        try {
            resulList = postRepository.findAllByDate(date);
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return resulList;
    }

    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
    public void removePost(Post post){
        try {
            postRepository.delete(post);
            newsEvent.fire(EPostEvent.DELETE);
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
    public List<Post> getCategoryPostsList() {
        if (currentPostTab.name().equals(EPostTab.ALL.name())) {
            return getAllPostsList();
        } else {
            List<Post> resulList = null;
            try {
                resulList = postRepository.findAllByCategory(currentPostTab.toString());
            } catch (RepositoryException | NoResultException e) {
                logger.log(Level.WARNING, e.getMessage());
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
            return resulList;
        }
    }

    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
    public List<Post> getAuthorPostsList() {
        List<Post> resulList = null;
        try {
            User user = (User) (userRepository.findByName(sessionHandle.getUserName()).orElseGet(null));
            resulList = postRepository.findByAuthor(user);
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return resulList;
    }

    public List<String> getCategoryStringList() {
        List<String> categoryList = new ArrayList<>();
        for (EPostTab p : EPostTab.values()) {
            categoryList.add(p.getCategory());
        }
        return categoryList;
    }

    public void showPostDialog(Post viewPost) {
        setViewPost(viewPost);
        generalBeanTools.executePrimeFacesScript("PF('postDlg').show();");
    }

    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
    private LineChartModel createNumberOfPostGraph() {

        try {
            User user = (User) (userRepository.findByName(sessionHandle.getUserName()).orElseGet(null));
            List<Post> postByUser = postRepository.findByAuthor(user);

            Function<Post, Month> dateGroup = new Function<Post, Month>() {
                @Override
                public Month apply(Post p) {
                    Date date = p.getDate();
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return localDate.getMonth();
                }
            };

            //group posts by month
            Map<Month, List<Post>> postsByMonth = postByUser.stream().collect(Collectors.groupingBy(dateGroup));

            List<Object> values = new ArrayList<>();
            List<String> labels = new ArrayList<>();

            for (int i = 1; i <= 12; i++) {
                if (postsByMonth.containsKey(Month.of(i))) {
                    values.add(postsByMonth.get(Month.of(i)).size());
                } else {
                    values.add(0);
                }
                labels.add(EMonth.values()[i - 1].getFullMonth());
            }
            return GraphCreator.createLinearGraph("Posts", "Number of Posts", values, labels);

        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        throw new RuntimeException("Error Post -- (Create LineGraph) Something went wrong");
    }

    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
    public DonutChartModel createCategoryNumberGraph() {
        try {
            User user = (User) (userRepository.findByName(sessionHandle.getUserName()).orElseGet(null));
            List<Post> postByUser = postRepository.findByAuthor(user);

            Function<Post, String> categoryGroup = new Function<Post, String>() {
                @Override
                public String apply(Post p) {
                    return currentPostTab.convertCategoryName(p.getCategory());
                }
            };

            //group posts by category
            Map<String, List<Post>> postsByCategory = postByUser.stream().collect(Collectors.groupingBy(categoryGroup));

            List<Number> values = new ArrayList<>();
            List<String> labels = new ArrayList<>();

            int categoryTypeNumber = EPostTab.values().length - 1;
            for (int i = 0; i < categoryTypeNumber; i++) {
                String categoryStr = EPostTab.values()[i].getCategory();
                if (!categoryStr.equals(EPostTab.ALL.getCategory())) {

                    if (postsByCategory.containsKey(categoryStr)) {
                        values.add(postsByCategory.get(categoryStr).size());
                    } else {
                        values.add(0);
                    }
                    labels.add(categoryStr);
                }
            }
            return GraphCreator.createDonutGraph(categoryTypeNumber, values, labels);

        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        throw new RuntimeException("Error Post -- (Create DonutGraph) Something went wrong");
    }


}
