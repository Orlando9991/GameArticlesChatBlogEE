/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function setRenderSubMenu(isRender){
    let menuDocument = document.getElementById("articles-form");
    if(isRender){
        menuDocument.style.display = 'block';
    }else{
        menuDocument.style.display = 'none';
    }
}

function toggleSubmitLogin(){
    let submitButton = document.getElementById("j_submit");
    let userNameInput = document.getElementById("j_username").value;
    let passwordInput = document.getElementById("j_password").value;
    
    if(!userNameInput || !passwordInput || userNameInput==='' || passwordInput==='')
        submitButton.disabled = true;
    else
        submitButton.disabled = false;
}

