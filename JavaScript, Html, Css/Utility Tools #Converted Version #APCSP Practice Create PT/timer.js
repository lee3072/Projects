var x;
var minute = 0;
var second = 0;
function Reload(){
    document.getElementById('minute').value =localStorage.getItem('minute');
    document.getElementById('second').value =localStorage.getItem('second');
}
function OnEvent(e){
    if(e.id=="startButton"){
        if(document.getElementById('startButton').innerText=="start"){
            x = setInterval(function(){
                // var now =new Date().getTime();
                // var distance =now- countDownStart;
                if(second!=59){
                    second++;
                }else{
                    second=0;
                    minute++;
                }
                
                
                if(minute<10){
                    document.getElementById('minute').innerText = '0'+ minute;
                } else{
                    document.getElementById('minute').innerText = minute;
                }
                if(second<10){
                    document.getElementById('second').innerText = '0'+ second;    
                } else{
                    document.getElementById('second').innerText = second;    
                }
                
            },1000);
            document.getElementById('startButton').innerText="stop";
        }else{
            clearInterval(x);
            document.getElementById('startButton').innerText="start";
        }
        
    } else {
        clearInterval(x);
        second=0;
        minute=0;
        document.getElementById('minute').innerText = '0'+ minute;
        document.getElementById('second').innerText = '0'+ second; 
        document.getElementById('startButton').innerText="start";
    }
    
}
// var countDownStart = new Date().getTime();
