function loadECharData(myData,chart){
    // 指定图表的配置项和数据
    option = {
        title: {
            text: myData.title
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            data:myData.tips
        },
        xAxis: [
            {
                type: 'category',
                data: myData.xData,
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '数量',
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [
            {
                name:myData.amountName,
                type:'bar',
                data:myData.amount
            },
            {
                name:myData.sumName,
                type:'line',
                data:myData.sum
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    chart.setOption(option);
}
function loadPieChartData(title,name,myData,chart){
    var option = {
        title : {
            text: title,
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: myData.legendData
        },
        series : [
            {
                name: name,
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:myData.seriesData,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    chart.setOption(option);
}
function loadVisitTimeLineChart(data,chart) {
    var option = {
        title: {
            text: data.title
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        toolbox: {
            feature: {
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        xAxis: {
            type: 'category',
            data: data.x,
            axisPointer: {
                type: 'shadow'
            }
        },
        yAxis: {
            type: 'value',
            name: 'pv',
            axisLabel: {
                formatter: '{value}'
            }
        },
        series: [{
            name:data.yname,
            data: data.y,
            type: 'line'
        }]
    };
    chart.setOption(option);
}
function loadAllCharts(chartsList) {
    if(chartsList.PublishDate){
        $.post("/admin/getStatisticalDataOfPublishDate",{},function (data,status) {
            if(data){
                var myData={};
                myData.title="博客发布日期统计图";
                myData.tips=["博客数量","博客总量"];
                myData.xData=[];
                myData.amountName="当天发布博客数量";
                myData.sumName="发布文章总和";
                myData.amount=[]
                myData.sum=[]
                var sum=0;
                for(var i in data){
                    var s=data[i];
                    myData.xData.push(s.publishDate);
                    myData.amount.push(s.dailyCount);
                    sum+=s.dailyCount;
                    myData.sum.push(sum);
                }
                loadECharData(myData,chartsList.PublishDate);
                // console.log(data);
            }
        });
    }
    if(chartsList.TagsArticleCount){
        $.post("/admin/getTagsOfArticleCountStatistics",{},function (data,status) {
            if(data){
                var xData={legendData:[],seriesData:[]};
                for(var i in data){
                    var s=data[i];
                    xData.legendData.push(s.value);
                    xData.seriesData.push({value:s.amount,name:s.value});
                    // xData.push({value:s.amount,name:});
                }
                loadPieChartData("文章标签统计图","文章数量",xData,chartsList.TagsArticleCount);
            }
        });
    }
    if(chartsList.PageViewCount){
        $.post("/admin/getPageViewTimesStatistics",{},function (data,status) {
            if(data){
                var myData={};
                myData.x=[];
                myData.y=[];
                myData.title='浏览数据统计';
                myData.yname='访问量';
                for(var i in data){
                    var visit=data[i];
                    myData.x.push(visit.visitTime);
                    myData.y.push(visit.pv);
                }
                loadVisitTimeLineChart(myData,chartsList.PageViewCount);
            }
        });
    }
}