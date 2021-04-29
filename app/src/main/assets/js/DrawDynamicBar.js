
var chartDom = document.getElementById('main');
var myChart = echarts.init(chartDom);
var option;

function run (newdata) {
	//-------process data------------------
var splitdata = [];
splitdata = newdata.split("#");
var hashtag = [];
var count = [];

for(var i=0;i<splitdata.length;i++){
//	alert(i.toString()+"->"+splitdata[i]);
	if(i%2 == 1){
		hashtag[(i-1)/2] = splitdata[i];
	}
	if(i%2 ==0){
		count[i/2 - 1] = parseInt(splitdata[i]);
	}

}
console.log(hashtag);
console.log(count);
	//--------------------------
    option.series[0].data = count;
    option.yAxis.data = hashtag;
    myChart.setOption(option);
}


var giveData = function(data){
//---------------process data--------------
var splitdata = [];
splitdata = data.split("#");
var hashtag = [];
var count = [];

for(var i=0;i<splitdata.length;i++){
//	alert(i.toString()+"->"+splitdata[i]);
	if(i%2 == 1){
		hashtag[(i-1)/2] = splitdata[i];
	}
	if(i%2 ==0){
		count[i/2 - 1] = parseInt(splitdata[i]);
	}

}
console.log(hashtag);
console.log(count);
//------------------------------------
var data = [];
data = count;
option = {
    xAxis: {
        max: 'dataMax',
    },
    yAxis: {
        type: 'category',
//      data: ['A', 'B', 'C', 'D', 'E'],
        data:hashtag,
        inverse: true,
        animationDuration: 300,
        animationDurationUpdate: 300,
        max: 5 // only the largest 6 bars will be displayed
    },
    series: [{
        realtimeSort: true,
        name: 'X',
        type: 'bar',
        data: data,
        label: {
            show: true,
            position: 'right',
            valueAnimation: true
        }
    }],
    legend: {
        show: true
    },
    animationDuration: 0,
    animationDurationUpdate: 3000,
    animationEasing: 'linear',
    animationEasingUpdate: 'linear'
};
option && myChart.setOption(option);

		}
var dataFromJava = "j";
window.onload = function (){

setInterval(function () {
dataFromJava = window.java.getDataFromJava();
console.log("data From java--->" + dataFromJava);
    run(dataFromJava);
}, 3000);

}

