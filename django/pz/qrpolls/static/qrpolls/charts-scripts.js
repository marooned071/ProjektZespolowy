/*
* Copyright 2014 Byliniak, Sliwka, Gambus, Celmer
*
* Licensed under the Surveys License, (the "License");
* you may not use this file except in compliance with the License.
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*/

google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart2);
	  

function drawChart2(divID,question,choices) {

	//zamiana &quot na ''
	choices = choices.replace(/&(l|g|quo)t;/g, function(a,b){
		return {
			l   : '<',
			g   : '>',
			quo : '"'
		}[b];
	})

	var c =$.parseJSON(choices);
	
	var data = google.visualization.arrayToDataTable(c);
	var t="";
	t= t.concat(question); 

	var options={};

	if(c[c.length-1][0]=='No Votes'){ //ustawienia gdy brak glosujacych
		options = {
			title: t,
			pieHole: 0.4,
			is3D : true,
			colors: ['#ffffff'], //kolor bialy
			backgroundColor: '#CECECE'

		};
	}
	else{
		options = {
			title: t,
			pieHole: 0.4,
			backgroundColor: '#CECECE',
			is3D : true
		};
	}
	var chart = new google.visualization.PieChart(document.getElementById(divID));
	chart.draw(data, options);
}
