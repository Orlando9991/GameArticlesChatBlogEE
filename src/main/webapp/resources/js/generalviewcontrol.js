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
            break;
        }
        case 'articles-form':{
            setCurrentTab('articles-form');
            break;
        }
        case 'news-page':{
            setCurrentTab('news-page');
            break;
        }
        case 'contact-page':{
            setCurrentTab('contact-page');
            break;
        }
            
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


