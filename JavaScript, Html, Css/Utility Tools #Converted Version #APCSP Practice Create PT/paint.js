var totalpage = 1;
var page=1;      
var paintList =[[]];  
var colorList = [[]];
var currentColor = "B";

if(localStorage.getItem('paintList')==null){
    paintList =[[]];
} else{
    paintList=JSON.parse(localStorage.getItem('paintList'));
}
if(localStorage.getItem('colorList')==null){
    colorList =[[]];
} else{
    colorList = JSON.parse(localStorage.getItem('colorList'));
    
}
if(localStorage.getItem('totalPage')==""){
    totalpage=1;
} else{
    totalpage = localStorage.getItem('totalPage');
}  


function setup(){
    
    createCanvas(290,290).parent('canvas-holder');
    stroke(0,0,0);
    loadPage();
}
function draw(){
    if(mouseIsPressed){
        ellipse(mouseX, mouseY, 1, 1);
        paintList[page-1].push([mouseX,mouseY]);
        colorList[page-1].push(currentColor);
        console.log(currentColor);
        console.log(paintList);
        console.log(colorList);
    }
    
}


function BLACK(){
    stroke(0,0,0);
    currentColor="B";
}
function YELLOW(){
    stroke(255,255,0);
    currentColor="Y";
}
function GREEN(){
    stroke(34,139,34);
    currentColor="G";
}
function resetColor(){
    if(currentColor=="B"){
        BLACK();
    } else if(currentColor=="Y"){
        YELLOW();
    } else if(currentColor=="G"){
        GREEN();
    }
}
function setColor(color){
    if(color=="B"){
        BLACK();
    } else if(color=="Y"){
        YELLOW();
    } else if(color=="G"){
        GREEN();
    }
}
function loadPage(){
    for (var i = 0; i < paintList[page-1].length; i++) {    // repeat every action stored in this page
        setColor(colorList[page-1][i]);                             
        ellipse(paintList[page-1][i][0],paintList[page-1][i][1],1,1);
    }// redraw the dots stored in the page
}
function randomNumber(start,end){
    return Math.floor(Math.random() * end) + start;  
}
function spraypaint(){
    for (var i = 0; i < paintList[page-1].length-1; i++) {                                                            // repeat every action stored in this page
        setColor(colorList[page-1][i]);                             
        for(var j=0;j<20;j++){      // repeat making dots
            ellipse(paintList[page-1][i][0]+randomNumber(-6,6),paintList[page-1][i][1]+randomNumber(-6,6),1,1); 
        }
    }
    
  } 
function OnEvent(e){
    
    if(e.id=="blackButton"){
        BLACK();
    } else if(e.id=="yellowButton"){
        YELLOW();
    } else if(e.id=="greenButton"){
        GREEN();
    } else if (e.id =="addPageButton"){
        totalpage++;
        page=totalpage;
        paintList.push([]);
        colorList.push([]);
        clear();
        resetColor();
        // document.getElementById('title').value ="";
        // document.getElementById('textArea').value ="";
        document.getElementById('currentPageButton').innerText = page;
    } else if (e.id=="previousPageButton"){
        if(page==1){
            page=totalpage;
        } else{
            page--;
        }
        if(totalpage!=1){
            clear();
            resetColor();
            loadPage();
        }
        // document.getElementById('title').value =titleList[pageNote-1];
        // document.getElementById('textArea').value =textList[pageNote-1];
        document.getElementById('currentPageButton').innerText = page;
    } else if (e.id=="nextPageButton"){
        if(page==totalpage){
            page=1;
        } else{
            page++;
               
        }
        if(totalpage!=1){
            clear();
            resetColor();
            loadPage();
        }
        // document.getElementById('title').value =titleList[pageNote-1];
        // document.getElementById('textArea').value =textList[pageNote-1];
        document.getElementById('currentPageButton').innerText = page;
    } else if (e.id=="deleteButton"){
        if(totalpage!=1){                      
            paintList.splice(page-1,1);
            colorList.splice(page-1,1);
            if (page!=1){                      
              page--;                           
            }
            clear();
            loadPage();
            totalpage--;                        
        } else {                                  
            paintList=[[]];                            
            colorList=[[]];                        
         
        }
        // document.getElementById('title').value =titleList[pageNote-1];
        // document.getElementById('textArea').value =textList[pageNote-1];
        document.getElementById('currentPageButton').innerText = page;
        
    } else if (e.id=="resetButton"){
        paintList=[[]];                            
        colorList=[[]];
        page=1;                                 
        totalpage=1;     
        clear();
        resetColor();
        document.getElementById('currentPageButton').innerText = page;                     
    } else if (e.id=="sprayButton"){
        spraypaint();
    }  else if (e.id=="starting"){
        localStorage.setItem('paintList',JSON.stringify(paintList));
        localStorage.setItem('colorList',JSON.stringify(colorList));
        localStorage.setItem('totalPage',totalpage);
    }
}


{
//------------------------Paint functions----------------------------//
    
//     //--------gobal variables for paint----------//
//     var totalpage=1;                                           // total page number variable
//     var page=1;                                                // current page number variable
//     var paintList =[[]];                                       // list of stored pictures
//     var sprayList = [false];                                   // list of spray for pages
//     var color="black";                                         // variable current color
//     var colorList = [[]];                                      // list of color per dot
//     var Green=setFillColor("rgba(26,188,156,0.4)");            // color green
//     var Black=setFillColor("rgba(16,27,24,0.4)");              // color black
//     var Yellow=setFillColor("rgba(179,178,42,0.4)");           // color yellow
//     var Mobile=0;                                              // determine whether it is mobile or not
//     var MobileList =[[]];                                      // list of mobile status
//     //-------------------------------------------//
    
//     {
//     // //--------------function for paint-----------//
//     // function spraypaint(){
//     //   for (var i = 0; i < paintList[page-1].length-1; i++) {                                                            // repeat every action stored in this page
//     //     setFillColor(colorList[page-1][i]);                     // sets the pen color   
//     //     for(var j=0;j<5;j++){                                                                                                               // repeat making dots          
//     //       if(MobileList[page-1][i]===0){
//     //         if(paintList[page-1][i].shiftKey===true){                                                                   // if shift key is true
//     //           circle(paintList[page-1][i].offsetX+randomNumber(-3,3),paintList[page-1][i].offsetY+randomNumber(-3,3),1);// draw smaller dots
//     //         }
//     //       }                    // if mobile mode is off
//     //       else{
//     //         circle(paintList[page-1][i].offsetX+randomNumber(-3,3),paintList[page-1][i].offsetY+randomNumber(-3,3),1);// draw smaller dots
//     //       }                                             // if mobile mode is on
//     //     }
//     //   }
      
//     // }                                  // spray paints the picture
//     // //-------------------------------------------//
//     }

//     function appendItem(array,itemToApeend){
//         array.push(itemToApeend);
//     }
//     function setText(id,text){
//         // if(document.getElementById(id).nodeType)
//         document.getElementById(id).innerText = text;
//     }

//     function OnEvent(e){
//         if(e.id=="addPageButton"){
//             totalpage++;                                        // adds a page to the canvas
//             appendItem(paintList,[]);                           // make a new storing list for drawing
//             appendItem(sprayList,false);                        // make a new storing list for spray
//             appendItem(colorList,[]);                      // make a new storing list for color
//             appendItem(MobileList,[Mobile]);
//         } else if (e.id =="nextPageButton"){
//             if(page<totalpage){                                       // if going to the next page is possible
//                 page+=1;                                                // go to the next page
//               }else{                                                    // if going to the next page is not possible
//                 page=1;                                                 // go to the first page
//               }
//               setText("currentPageButton",page);                          // displays the current page number 
//               clearCanvas();                                            // erase the drawing on other page
//               if (sprayList[page-1]===false){                           // if spray is false
//                 for (var i = 0; i < paintList[page-1].length; i++) {    // repeat every action stored in this page
//                   if(MobileList[page-1][i]===0){  
//                     if(paintList[page-1][i].shiftKey===true){                             // draw only the part with shift
//                       stroke(colorList[page-1][i]);                                   // sets the pen color for every dots
//                       circle(paintList[page-1][i].offsetX,paintList[page-1][i].offsetY,2);  // redraw the dot stored
//                     }
//                   }                    // if mobile mode is off
//                   else{
//                     setFillColor(colorList[page-1][i]);                                   // sets the pen color for every dots
//                     circle(paintList[page-1][i].offsetX,paintList[page-1][i].offsetY,2);  // redraw the dot stored
//                   }                                             // if mobile mode is off
//                 }// redraw the dots stored in the page
//               } else {                                                  // if spray is true
//                 spraypaint();                                           // redraw the dots in spraypaint
//               }
//         }
//     }
//     //--------------events for paint-------------//
//     // onEvent("addpageButton", "click", function() {
      
//     // });        // makes a additional page for canvas                                                 
//     // onEvent("nextpageButton","click",function(){
      
//     // });          // changes to the next drawing page                                               
//     onEvent("lastpageButton","click",function(){
//       if(page>1){                                               // if going to the last page is possible
//         page-=1;                                                // go to the last page
//       }else{                                                    // if going to the last page is not possible
//         page=totalpage;                                         // go to the final page
//       }
//       setText("pagenumberLabel",page);                          // displays the current page number 
//       clearCanvas();                                            // erase the drawing on other page
//       if (sprayList[page-1]===false){                           // if spray is false
//         for (var i = 0; i < paintList[page-1].length; i++) {    // repeat every action stored in this page
//           if(MobileList[page-1][i]===0){
//             if(paintList[page-1][i].shiftKey===true){                             // draw only the part with shift
//               setFillColor(colorList[page-1][i]);                                   // sets the pen color for every dots
//               circle(paintList[page-1][i].offsetX,paintList[page-1][i].offsetY,2);  // redraw the dot stored
//             }
//           }                    // if mobile status is off
//           else{
//             setFillColor(colorList[page-1][i]);                                   // sets the pen color for every dots
//             circle(paintList[page-1][i].offsetX,paintList[page-1][i].offsetY,2);  // redraw the dot stored
//           }                                             // if mobile status is on
//         }  // redraw the dots stored in the page
//       } else {                                                  // if spray is true
//         spraypaint();                                           // redraw the dots in spraypaint
//       }
//     });          // go to the last page                                               
//     onEvent("mainCanvas", "mousemove", function(event) {
//       if(Mobile===0){                                           // if mobile mode is off
//         if (event.shiftKey===true){                             // if shift key is on
//           if(sprayList[page-1]===false){                        // if spray is false
//             setFillColor(color);                                // sets the pen color
//             circle(event.offsetX, event.offsetY, 2);            // draw the circle on the position of mouse
//           }else{                                                // if spray is true
//             for(var j=0;j<5;j++){                                                                                         // repeat making dots          
//               if(event.shiftKey===true){                                                                   // if shift key is true
//                 setFillColor(color);                                                        // sets the pen color
//                 circle(event.offsetX+randomNumber(-3,3),event.offsetY+randomNumber(-3,3),1);// draw smaller dots
//               }                    // draw the small dots
//             }                           // draw the dots repeatedly
//           }
//         }
//       }
//       else{                                                   // if mobil mode is on
//         if(sprayList[page-1]===false){                        // if spray is false
//           setFillColor(color);                                // sets the pen color
//           circle(event.offsetX, event.offsetY, 2);            // draw the circle on the position of mouse
//         }else{                                                // if spray is true
//           for(var k=0;k<5;k++){                                                                                         // repeat making dots          
//             setFillColor(color);                                                        // sets the pen color
//             circle(event.offsetX+randomNumber(-3,3),event.offsetY+randomNumber(-3,3),1);// draw smaller dots
//           }                           // draw the dots repeatedly
//         }
//       }                                                 // if mobil mode is on
//       appendItem(paintList[page-1],event);                // saves the action to the page
//       appendItem(colorList[page-1],color);                // saves color with action
//       appendItem(MobileList[page-1],Mobile);              // saves Mobile status with action
//     });  // draw the picture while mouse is on canvas
//     onEvent("deletepageButton","click",function(){
//       if(totalpage!==1){                                      // if the total page is not 1
//         removeItem(paintList,page-1);                         // remove this page
//         removeItem(sprayList,page-1);                         // remove spray this page
//         removeItem(MobileList,page-1);                        // remove mobile status this page
//         if (page!==1){                                        // if the page is not 1
//           page--;                                             // goes to the remained page
//         }
//         totalpage--;                                          // reduce the total page number
//       } else {                                                // if the total page is 1
//         paintList=[[]];                                       // reset every page
//         sprayList=[false];                                    // reset spray on every page
//         MobileList=[[]];                                      // reset mobile status on every page
//       }
//       setText("pagenumberLabel",page);                        // displays the page number  
//       clearCanvas();                                          // clears the paint
//       if (sprayList[page-1]===false){                           // if spray is false
//         for (var i = 0; i < paintList[page-1].length; i++) {                    // repeat every action stored in this page
//           if(paintList[page-1][i].shiftKey===true){                             // draw only the part with shift
//            circle(paintList[page-1][i].offsetX,paintList[page-1][i].offsetY,2);  // redraw the dot stored
//           }
//         }  // redraw the dots stored in the page
//       } else {                                                  // if spray is true
//         spraypaint();                                           // redraw the dots in spraypaint
//       }
//     });        // deletes the current page
//     onEvent("resetpaintButton","click",function(){
//       clearCanvas();                                            // clear the canvs
//       page=1;                                                   // reset the page to 1
//       totalpage=1;                                              // reset the total page to 1
//       paintList=[[]];                                           // reset the stored actions
//       colorList=[[]];                                           // reset the color list
//       sprayList=[false];                                        // reset the stored spray
//       MobileList=[[]];                                          // reset the stored mobile status
//       setText("pagenumberLabel",page);                          // displays the page number
//     });        // resets the canvas
//     onEvent("SprayPaintButton","click",function(){
//       clearCanvas();                                            // clear the page
//       spraypaint();                                             // change the picture to spray paint
//       sprayList[page-1]=true;                                   // store that this page is spray paint
//     });        // spray paints the page
//     onEvent("greenButton","click",function(){
//       Green;                                                    // set the pen color to green
//       color="green";                                            // saves the color for the dot
//     });             // sets pen color to green
//     onEvent("blackButton","click",function(){
//       Black;                                                    // set the pen color to black
//       color="black";                                            // saves the color for the dot
//     });             // sets pen color to black
//     onEvent("yellowButton","click",function(){
//       Yellow;                                                    // set the pen color to yellow
//       color="yellow";                                            // saves the color for the dot
//     });            // sets pen color to yellow
//     onEvent("mobileButton","click",function(){
//       if(Mobile===0){
//         setProperty("mobileButton","background-color","#35b720"); // display that mobile is on
//         Mobile=1;                                                 // turn Mobile on
//       } 
//       else{
//         setProperty("mobileButton","background-color","#bd1313"); // display that mobile is off 
//         Mobile=0;                                                 // turn mobile off
//       }
//     });            // turn mobile mode on and off
//     //-------------------------------------------//
//   //-------------------------------------------------------------------//
  






















}