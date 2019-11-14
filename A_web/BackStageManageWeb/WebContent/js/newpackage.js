 $(function() {
        $("div").click(function () {
            $(this).addClass("select");
        });

        $("#btnPrint").click(function print() {
            $("#printArea").jqprint({
                debug: false,
                importCSS: true,
                printContainer: true,
                operaSupport: false
            });
        });

        function randomNum(minNum, maxNum) {

            var today = new Date();
            var day = today.getDate(); //获取当前日(1-31)
            var month = today.getMonth() + 1; //显示月份比实际月份小1,所以要加1
            var year = today.getYear();  //获取完整的年份(4位,1970-????)  getFullYear()
            var years = today.getFullYear();
            years = years < 99 ? "20" + years : years;
            month = month < 10 ? "0" + month : month;  //数字<10，实际显示为，如5，要改成05
            day = day < 10 ? "0" + day : day;
            var hh = today.getHours();
            hh = hh < 10 ? "0" + hh : hh;
            var ii = today.getMinutes();
            ii = ii < 10 ? "0" + ii : ii;
            var ss = today.getSeconds();
            ss = ss < 10 ? "0" + ss : ss;
            var dada = years + month + day + hh + ii + ss;//时间不能直接相加，要这样相加！！！14位

            switch (arguments.length) {
                case 1:
                    return dada + parseInt(Math.random() * minNum + 1, 10);
                    break;
                case 2:
                    return dada + parseInt(Math.random() * (maxNum - minNum + 1) + minNum, 10);
                    break;
                default:
                    return 0;
                    break;
            }
        }


        $("#sc_ydh").click(function () {
            var ydh = document.getElementById("ydh");
            
            var aa = randomNum(1000, 9999);
            ydh.value = aa;
            $("#bcTarget").barcode(aa, "code128", {
                output: 'css',       //渲染方式            
                color: '#000',   //条码颜色
                barWidth: 2,        //单条条码宽度
                barHeight: 100,     //单体条码高度
                addQuietZone: false  //是否添加空白区（内边距）
            });

        });


    })
