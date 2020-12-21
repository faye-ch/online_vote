// var mapUrl = "getSource/"+param;
// axios.get(mapUrl).then(({data})=>{
//     const list=data
//     var map = echarts.init(document.getElementById('map'));
//     map.showLoading();
//     map.hideLoading();
//     option = {
//         title: {
//             text: '',
//             left: 'right'
//         },
//
//         visualMap: {
//             left: 'right',
//             min: 500000,
//             max: 38000000,
//             inRange: {
//                 color: ['#313695', '#4575b4', '#74add1', '#abd9e9', '#e0f3f8', '#ffffbf', '#fee090', '#fdae61', '#f46d43', '#d73027', '#a50026']
//             },
//             text: ['High', 'Low'],           // 文本，默认为数值文本
//             calculable: true
//         },
//         toolbox: {
//             show: true,
//             //orient: 'vertical',
//             left: 'left',
//             top: 'top',
//             feature: {
//                 dataView: {readOnly: false},
//                 restore: {},
//                 saveAsImage: {}
//             }
//         },
//         series: [
//             {
//                 name: 'CHN',
//                 type: 'map',
//                 roam: true,
//                 map: 'china',
//                 emphasis: {
//                     label: {
//                         show: true
//                     }
//                 },
//                 // 文本位置修正
//                 textFixed: {
//                     Alaska: [20, -20]
//                 },
//                 data:list
//             }
//         ]
//     };
//     map.setOption(option);
//
// })
