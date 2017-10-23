
// chart sample data
var arrVisitors = new Array();
arrVisitors[0] = "周日, 750";
arrVisitors[1] = "周已, 425";
arrVisitors[2] = "周日, 960";
arrVisitors[3] = "周日, 700";
arrVisitors[4] = "2011, 800";
arrVisitors[5] = "2012, 975";
var canvas;
var context;
// chart properties
var cWidth, cHeight, cMargin, cSpace;
var cMarginSpace, cMarginHeight;
// bar properties
var bWidth, bMargin, totalBars, maxDataValue;
var bWidthMargin;
// bar animation
var ctr, numctr, speed;
// axis property
var totLabelsOnYAxis;
// barchart constructor
function barChart() {
    canvas = document.getElementById('bchart');
    if (canvas && canvas.getContext) {
        context = canvas.getContext('2d');
    }
    chartSettings();
    drawAxisLabelMarkers();
    drawChartWithAnimation();
//    delData();
}
// initialize the chart and bar values
function chartSettings() {
    // chart properties
    cMargin = 25;
    cSpace = 60;
    cHeight = canvas.height - 2 * cMargin - cSpace;
    cWidth = canvas.width - 2 * cMargin - cSpace;
    cMarginSpace = cMargin + cSpace;
    cMarginHeight = cMargin + cHeight;
    // bar properties
    bMargin = 15;
    totalBars = arrVisitors.length;
    bWidth = (cWidth / totalBars) - bMargin;
    // find maximum value to plot on chart
    maxDataValue = 0;
    for (var i = 0; i < totalBars; i++) {
        var arrVal = arrVisitors[i].split(",");
        var barVal = parseInt(arrVal[1]);
        if (parseInt(barVal) > parseInt(maxDataValue))
            maxDataValue = barVal;
    }
    totLabelsOnYAxis = 10;
    context.font = "10pt Garamond";

    // initialize Animation variables
    ctr = 0;
    numctr = 100;
    speed = 10;
}
// draw chart axis, labels and markers
function drawAxisLabelMarkers() {
    context.lineWidth = "2.0";
    // draw y axis
    drawAxis(cMarginSpace, cMarginHeight, cMarginSpace, cMargin);
    // draw x axis
    drawAxis(cMarginSpace, cMarginHeight, cMarginSpace + cWidth, cMarginHeight);
    context.lineWidth = "1.0";
    drawMarkers();
}
// draw X and Y axis
function drawAxis(x, y, X, Y) {
    context.beginPath();
    context.moveTo(x, y);
    context.lineTo(X, Y);
    context.closePath();
    context.stroke();
}
// draw chart markers on X and Y Axis
function drawMarkers() {
    var numMarkers = parseInt(maxDataValue / totLabelsOnYAxis);
    context.textAlign = "right";
    context.fillStyle = "#000";;
    // Y Axis
    for (var i = 0; i <= totLabelsOnYAxis; i++) {
        markerVal = i * numMarkers;
        markerValHt = i * numMarkers * cHeight;
        var xMarkers = cMarginSpace - 5;
        var yMarkers = cMarginHeight - (markerValHt / maxDataValue);
        context.fillText(markerVal, xMarkers, yMarkers, cSpace);
    }
    // X Axis
    context.textAlign = 'center';
    for (var i = 0; i < totalBars; i++) {
        arrval = arrVisitors[i].split(",");
        name = arrval[0];
        markerXPos = cMarginSpace + bMargin
            + (i * (bWidth + bMargin)) + (bWidth/2);
        markerYPos = cMarginHeight + 10;
        context.fillText(name, markerXPos, markerYPos, bWidth);
    }
    context.save();
    // Add Y Axis title
    context.translate(cMargin + 10, cHeight / 2);
    context.rotate(Math.PI * 90 / 180);
   // context.fillText('Visitors in Thousands', 0, 0);
    context.restore();
    // Add X Axis Title
    context.fillText('Year Wise', cMarginSpace +
        (cWidth / 2), cMarginHeight + 30 );
}
function drawChartWithAnimation() {
    // Loop through the total bars and draw
    for (var i = 0; i < totalBars; i++) {
        var arrVal = arrVisitors[i].split(",");
        bVal = parseInt(arrVal[1]);
        bHt = (bVal * cHeight / maxDataValue) / numctr * ctr;
        bX = cMarginSpace + (i * (bWidth + bMargin)) + bMargin;
        bY = cMarginHeight - bHt - 2;
        drawRectangle(bX, bY, bWidth, bHt, true);
    }
    // timeout runs and checks if bars have reached
    // the desired height; if not, keep growing
    if (ctr < numctr) {
        ctr = ctr + 1;
        setTimeout(arguments.callee, speed);
    }
}
function drawRectangle(x, y, w, h, fill) {
    context.beginPath();
    context.rect(x, y, w, h);
    context.closePath();
    context.stroke();
    if (fill) {
        var gradient = context.createLinearGradient(0, 0, 0, 300);
        gradient.addColorStop(0, 'green');
        gradient.addColorStop(1, 'rgba(67,203,36,.15)');
        context.fillStyle = gradient;
        context.strokeStyle = gradient;
        context.fill();
    }
}

/*function delData(){
	var data = document.getElementById('metadata').value;
	var obj = new Function("return" + data)();
	var ele_weeklySumStep = document.getElementById('weeklySumStep');
	ele_weeklySumStep.innerHTML=obj.respData.sportsummary.weeklySumStep+"步";
	var ele_rank = document.getElementById('rank');
	ele_rank.innerHTML = obj.respData.sportsummary.rank;
	var ele_week = document.getElementById('week');
	var weekStar = new Date(obj.respData.sportsummary.weekSatr);
	Y = weekStar.getFullYear() + '.';
	M = (weekStar.getMonth()+1 < 10 ? '0'+(weekStar.getMonth()+1) : weekStar.getMonth()+1)+".";
	D = weekStar.getDate() <10 ? '0'+weekStar.getDate() : weekStar.getDate();
	var weekEnd = new Date(obj.respData.sportsummary.weekEnd);
	Y1 = weekEnd.getFullYear() + '.';
	M1 = (weekEnd.getMonth()+1 < 10 ? '0'+(weekEnd.getMonth()+1) : weekEnd.getMonth()+1)+".";
	D1 = weekEnd.getDate() < 10 ? '0'+ weekEnd.getDate() : weekEnd.getDate();
	ele_week.innerHTML = M+D+"-"+M1+D1;
	var ele_goalDays = document.getElementById('goalDays');
	ele_goalDays.innerHTML = obj.respData.sportsummary.goalDays;
	var ele_weeklyAvgStep = document.getElementById('weeklyAvgStep');
	ele_weeklyAvgStep.innerHTML = obj.respData.sportsummary.weeklyAvgStep;
}*/