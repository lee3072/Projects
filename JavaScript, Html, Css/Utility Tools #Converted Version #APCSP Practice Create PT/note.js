var titleList =[""];
var textList =[""];
var totalpageNote = 1;
var pageNote=1;         
if(localStorage.getItem('titleList')==""){
    titleList =[""];
} else{
    titleList=JSON.parse(localStorage.getItem('titleList'));
}
if(localStorage.getItem('textList')==""){
    textList =[""];
} else{
    textList = JSON.parse(localStorage.getItem('textList'));
    
}
if(localStorage.getItem('totalPageNote')==""){
    totalpageNote=1;
} else{
    totalpageNote = localStorage.getItem('totalPageNote');
}                                     




function OnEvent(e){
    if(e.id=="title"){
        titleList[pageNote-1]=
        document.getElementById(e.id).value;
    } else if (e.id =="textArea"){
        textList[pageNote-1]=
        document.getElementById(e.id).value;
    } else if (e.id =="addPageButton"){
        totalpageNote++;
        pageNote=totalpageNote;
        titleList.push("");
        textList.push("");
        document.getElementById('title').value ="";
        document.getElementById('textArea').value ="";
        document.getElementById('currentPageButton').innerText = pageNote;
    } else if (e.id=="previousPageButton"){
        if(pageNote==1){
            pageNote=totalpageNote;
        } else{
            pageNote--;
        }
        document.getElementById('title').value =titleList[pageNote-1];
        document.getElementById('textArea').value =textList[pageNote-1];
        document.getElementById('currentPageButton').innerText = pageNote;
    } else if (e.id=="nextPageButton"){
        if(pageNote==totalpageNote){
            pageNote=1;
        } else{
            pageNote++;
        }
        document.getElementById('title').value =titleList[pageNote-1];
        document.getElementById('textArea').value =textList[pageNote-1];
        document.getElementById('currentPageButton').innerText = pageNote;
    } else if (e.id=="deleteButton"){
        if(totalpageNote!==1){                      
            titleList.splice(pageNote-1,1);
            textList.splice(pageNote-1,1);      
            if (pageNote!==1){                      
              pageNote--;                           
            }
            totalpageNote--;                        
        } else {                                  
            titleList=[""];                         
            textList=[""];                          
        }
        document.getElementById('title').value =titleList[pageNote-1];
        document.getElementById('textArea').value =textList[pageNote-1];
        document.getElementById('currentPageButton').innerText = pageNote;
        
    } else if (e.id=="resetButton"){
        titleList=[""];                            
        textList=[""];                              
        pageNote=1;                                 
        totalpageNote=1;     
        document.getElementById('title').value ="";
        document.getElementById('textArea').value ="";
        document.getElementById('currentPageButton').innerText = pageNote;                     
    } else if (e.id=="starting"){
        localStorage.setItem('titleList',JSON.stringify(titleList));
        localStorage.setItem('textList',JSON.stringify(textList));
        localStorage.setItem('totalPageNote',totalpageNote);
    }
    console.log(titleList);
    console.log(textList);
    
}
