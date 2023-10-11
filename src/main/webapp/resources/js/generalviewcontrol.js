/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


const CssVisibility = {
    Visible: 'block',
    NotVisible: 'none'
};

class tabView {
    constructor(title, isActive) {
        this.title = title;
        this.isActive = isActive;
    }
    
    setIsVisible(toggle){
        this.isActive = toggle;
        if(toggle===true){
            setDisplay(this.title,CssVisibility.Visible);
        }else{
            setDisplay(this.title,CssVisibility.NotVisible);
        } 
    }
}

const homeTab = new tabView('home-page',true);
const articlesTab = new tabView('articles-form',true);
const newTab = new tabView('news-page',true);
const contactTab = new tabView('contact-page',true);

const menuTabs = [];

menuTabs.push(homeTab);
menuTabs.push(articlesTab);
menuTabs.push(newTab);
menuTabs.push(contactTab);

function setCurrentTab(name){
    
    menuTabs.forEach((t) => {
        if(t.title === name){
            t.setIsVisible(true);
        }else{
            t.setIsVisible(false);
        }
    });


}

function setActiveRenderSubMenu(id){
    switch(id){
        case 'home-page':{         
            setCurrentTab('home-page');
            setHomeBackGroundOpc(false);
            break;
        }
        case 'articles-form':{
            setCurrentTab('articles-form');
            setHomeBackGroundOpc(true);
            break;
        }
        case 'news-page':{
            setCurrentTab('news-page');
            setHomeBackGroundOpc(true);
            break;
        }
        case 'contact-page':{
            setCurrentTab('contact-page');
            setHomeBackGroundOpc(true);
            break;
        }
            
    }
    return true;
}


function setHomeBackGroundOpc(isSet){
    if(isSet){
        document.body.style.backgroundImage = "url(/GameBlog/javax.faces.resource/img/backgroung.jpg.xhtml)";
    }else{
        document.body.style.backgroundImage = "";
    }
    
}

function setDisplay(id,typeDisplay){
    let menuDocument = document.getElementById(id);
    menuDocument.style.display = typeDisplay;
}

function toggleSubmitLogin(){
    let submitButton = document.getElementById('j_submit');
    let userNameInput = document.getElementById('j_username').value;
    let passwordInput = document.getElementById('j_password').value;
    
    if(!userNameInput || !passwordInput || userNameInput==='' || passwordInput==='')
        submitButton.disabled = true;
    else
        submitButton.disabled = false;
}

document.addEventListener('scroll',()=>{
    let home = document.getElementById("home-page");
    let initiate = document.getElementById("initiate");
    resetHomeTitlesPos(initiate, title);
    if(home.style.display !== 'none'){
       
        let characters = document.getElementById("characters");
        let arrow = document.getElementById("arrow");
        let title = document.getElementById("title");
        let mario = document.getElementById("mario");
        
        let value = window.scrollY;
        
        title.style.top = value * 0.9 + "px";
        characters.style.top = value * -0.5 + "px";
        mario.style.top = value * -0.5 + "px";
        arrow.style.opacity = 1-(value * 0.003);
        
        if(value<200){
            resetHomeTitlesPos(initiate, title);
        }else if(value>=200){
            title.textContent = "Welcome";
            arrow.style.opacity = 0;
            initiate.classList.remove("hidden");
        }
        
    }},{passive:true});

function resetHomeTitlesPos(initiate, title){
    if(!initiate.classList.contains("hidden")){
        initiate.classList.add("hidden");
    }
    title.textContent = "GameBlog";
}