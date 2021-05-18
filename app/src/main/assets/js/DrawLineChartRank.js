var chartDom = document.getElementById('main');
var myChart = echarts.init(chartDom);
var option;

option = {
    title: {
        text: 'Hashtag Rank'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['Germany', 'China', 'Football', 'Corona', 'Virus']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['2021-05-10', '2021-05-11', '2021-05-12', '2021-05-13', '2021-05-14', '2021-05-15', '2021-05-16']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name: 'Germany',
            type: 'line',
            stack: 'rank',
            data: [12, 13, 11, 13, 9, 2, 21]
        },
        {
            name: 'China',
            type: 'line',
            stack: 'rank',
            data: [20, 12, 19, 24, 90, 30, 10]
        },
        {
            name: 'Football',
            type: 'line',
            stack: 'rank',
            data: [10, 23, 1, 15, 19, 30, 4]
        },
        {
            name: 'Corona',
            type: 'line',
            stack: 'rank',
            data: [3, 32, 31, 28, 9, 3, 5]
        },
        {
            name: 'Virus',
            type: 'line',
            stack: 'rank',
            data: [8, 93, 101, 34, 190, 30, 20]
        }
    ]
};

option && myChart.setOption(option);