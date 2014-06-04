google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart2);
	  

function drawChart2(divID,question,choices) {

	choices = choices.replace(/&(l|g|quo)t;/g, function(a,b){
		return {
			l   : '<',
			g   : '>',
			quo : '"'
		}[b];
	})

	var data = google.visualization.arrayToDataTable($.parseJSON(choices));
	var t="";
	t= t.concat(question); 
	var options = {
		title: t,
		pieHole: 0.4,
		is3D : true
	};

	var chart = new google.visualization.PieChart(document.getElementById(divID));
	chart.draw(data, options);
}