var chartDom = document.getElementById('main');
var myChart = echarts.init(chartDom);
var option;

option = {
    title: {
        text: 'Hashtag Amount'
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
            stack: 'total',
            data: [120, 132, 101, 134, 90, 230, 210]
        },
        {
            name: 'China',
            type: 'line',
            stack: 'total',
            data: [220, 182, 191, 234, 290, 330, 310]
        },
        {
            name: 'Football',
            type: 'line',
            stack: 'total',
            data: [150, 232, 201, 154, 190, 330, 410]
        },
        {
            name: 'Corona',
            type: 'line',
            stack: 'total',
            data: [320, 332, 301, 334, 390, 330, 320]
        },
        {
            name: 'Virus',
            type: 'line',
            stack: 'total',
            data: [820, 932, 901, 934, 1290, 1330, 1320]
        }
    ]
};

option && myChart.setOption(option);