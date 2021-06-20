layui.use(['layer','echarts'], function () {
    var $ = layui.jquery,
        echarts = layui.echarts;

    var chartDom = document.getElementById('make');
    var myChart = echarts.init(chartDom);
    var option;

    $.get(ctx+"/report/countCustomerMake",function(data){
        option = {
            tooltip:{},
            xAxis: {
                type: 'category',
                data: data.customerLevelList
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: data.customerCountList,
                type: 'line'
            }]
        };

        option && myChart.setOption(option);

    })
    $.get(ctx+"/report/countCustomerMake2",function(data){

        var chartDom = document.getElementById('make02');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
            title: {
                text: '客户构成分析',
                subtext: '来自CRM',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                left: 'center',
                top: 'bottom',
                data: data.customerLevelList
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: [
                {
                    name: '半径模式',
                    type: 'pie',
                    radius: [20, 140],
                    center: ['25%', '50%'],
                    roseType: 'radius',
                    itemStyle: {
                        borderRadius: 5
                    },
                    label: {
                        show: false
                    },
                    emphasis: {
                        label: {
                            show: true
                        }
                    },
                    data:data.customerList
                },
                {
                    name: '面积模式',
                    type: 'pie',
                    radius: [20, 140],
                    center: ['75%', '50%'],
                    roseType: 'area',
                    itemStyle: {
                        borderRadius: 5
                    },
                    data: data.customerList
                }
            ]
        };

        option && myChart.setOption(option);

    })

});