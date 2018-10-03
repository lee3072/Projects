
function Reload(){document.getElementById('display').value =localStorage.getItem('display');}
function OnEvent(e){
    if(e.id=="enterButton"){
        document.getElementById('display').value=eval(document.getElementById('display').value);
    } else if (e.id=="clearButton") {
        document.getElementById('display').value="";
    } else if (e.id=="multiplyButton") {
        document.getElementById('display').value +=document.getElementById(e.id).value;
    } else{
        document.getElementById('display').value +=document.getElementById(e.id).innerText;
    }
    localStorage.setItem('display',document.getElementById('display').value)
}