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
    resetHomeCharactersTop();
    resetHomeTitleStyle();
    resetScroll();
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
    noOpacityStyle(initiate);
    
    if(home.style.display !== 'none'){
        let characters = document.getElementById("characters");
        let arrow = document.getElementById("arrow");
        let title = document.getElementById("title");
        let mario = document.getElementById("mario");
        let initiate = document.getElementById("initiate");
        let value = window.scrollY;
        
        title.style.top = value * 0.9 + "px";
        characters.style.top = value * -0.5 + "px";
        mario.style.top = value * -0.5 + "px";
        arrow.style.opacity = 1-(value * 0.003);
        title.style.opacity = 1-(value * 0.003);
        initiate.style.opacity = 0 + (value * 0.004);
        
        if(value>=200){
            arrow.style.opacity = 0;
        }   
    }},{passive:true});

function resetHomeTitleStyle(){
    let title = document.getElementById("title");
    resetTopPosition(title);
    fullOpacityStyle(title);
}

function resetHomeCharactersTop(){
     let characters = document.getElementById("characters");
     let mario = document.getElementById("mario");
     resetTopPosition(characters);
     resetTopPosition(mario);
}

function resetTopPosition(element){
    element.style.top = 0;
}

function fullOpacityStyle(element){
    element.style.opacity = 1;
}
function noOpacityStyle(element){
    element.style.opacity = 0;
}

function resetScroll() {
  window.scrollTo(0, 0);
}