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
import com.gameblog.app.tools.ImageByteConverter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author orlan
 */
@Named("PostHandle")
@SessionScoped
public class PostHandle implements Serializable{
    
    @Inject
    PostRepository postFacade;
    
    @Inject
    UserRepository userFacade;
    
    @Inject
    SessionHandle sessionHandle;
    
    @Inject
    GeneralViewTools beanTools;
    
    private UploadedFile imgFile;
    
    private Post post;
    
    private Post viewPost;
    
    private PostTab currentPostTab;
    
    public enum PostTab{
        ALL("All"),
        ACTION("Action"),
        ADVENTURE("Adventure"),
        RPG("RPG"),
        SIMULATION("Simulation"),
        PUZZLE("Puzzle"),
        SPORTS_RACING("Sports/Racing");
        
        private final String category;
        
        PostTab(String category){
            this.category = category;
        }
        
        public String getCategory(){
            return this.category;
        };
    }
    
    public PostHandle(){
        setNewPost();
        currentPostTab = PostTab.ALL;
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
    
    private void setNewPost(){
        post = new Post();
    }
    
    public void setPostCategory(TabChangeEvent event){
        String title = event.getTab().getTitle().replaceFirst("/", "_").toUpperCase();
        switch (PostTab.valueOf(title)) {
            case ALL:
                this.currentPostTab = PostTab.ALL;
                break; 
            case ACTION:
                this.currentPostTab = PostTab.ACTION;
                break;
            case ADVENTURE:
                this.currentPostTab = PostTab.ADVENTURE;
                break;
            case RPG:
                this.currentPostTab = PostTab.RPG;
                break;
            case SIMULATION:
                this.currentPostTab = PostTab.SIMULATION;
                break;
            case PUZZLE:
                this.currentPostTab = PostTab.PUZZLE;
                break;             
            case SPORTS_RACING:
                this.currentPostTab = PostTab.SPORTS_RACING;
                break; 
            default:
                this.currentPostTab = PostTab.ALL;
        }
    }
         
    @Transactional
    public void createPost() throws ServletException, IOException{
        User user = (User)(userFacade.findByName(sessionHandle.getUserName()).orElseGet(null));
        post.setUser(user);      
        post.setDate(new Date());
        postFacade.create(post);
        PrimeFaces.current().executeScript("PF('postCreatorDlg').hide();");
        showSucessMessage();
    }
    
    public void showSucessMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucess", "Post Created");
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public List <Post> allPostsList(){
        return postFacade.findAll();
    }
    
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public List<Post> getCategoryPostsList(){
        if(currentPostTab.name().equals(PostTab.ALL.name())){
            return allPostsList();
        }
        return postFacade.findAllByCategory(currentPostTab.toString());
    }
    
    public List<String> getCategoryStringList(){
        List <String> categoryList = new ArrayList<>();
        for(PostTab p: PostTab.values()){
            categoryList.add(p.getCategory());
        }
        return categoryList;
    }
    
    public void showPostDialog(Post viewPost){
        setViewPost(viewPost);
        beanTools.executePrimeFacesScript("PF('postDlg').show();");
    }
    
}
