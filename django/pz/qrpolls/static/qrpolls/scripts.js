function sayHello() {
	// alert("Hello World!");
	//document.writeln("<h1>Hello World!</h1><p>Have a nice day!</p>");

	var node=document.createElement("LI");
	var textnode=document.createTextNode("Water");
	node.appendChild(textnode);
	document.getElementById("myList").appendChild(node);
}

function addForm1() {

	var button = document.createElement("button")
	button.setAttribute("onClick","addAnswer1()");
	var t=document.createTextNode("+ odpowiedz");
	button.appendChild(t); 


	var input = document.createElement("input");
	input.setAttribute("id", "question"); 
	input.setAttribute("type", "text"); 
	input.setAttribute("name", "question"); 

	document.getElementById("newForm1").appendChild(input);
	document.getElementById("buttons").appendChild(button);
	
}

var choiceCounter = 0;


function createNewForm(){
	var form = document.createElement("form");
	form.setAttribute("method", "POST"); 
	form.setAttribute("action", "newQuestion");
	form.setAttribute("id", "newForm");

	//var CSRF_TOKEN = document.getElementById('hidden-csrf');

	var CSRF_TOKEN = document.getElementById('csrf_token').value;

	//var CSRF_TOKEN = "{{ csrf_token }}";


	var token = document.createElement("input");
	token.setAttribute("type", "hidden"); 
	token.setAttribute("name", "csrfmiddlewaretoken");
	token.setAttribute("value", CSRF_TOKEN);
	form.appendChild(token);


    var questionText = document.createElement("input"); 
    questionText.setAttribute("id", "question"); 
	questionText.setAttribute("type", "text"); 
	questionText.setAttribute("name", "question"); 

	form.appendChild(questionText);

	document.body.appendChild(form);


	var buttonAdd = document.createElement("button");
	buttonAdd.setAttribute("onClick","addChoice()");
	var t=document.createTextNode("+ odpowiedz");
	buttonAdd.appendChild(t); 
	document.getElementById("buttons").appendChild(buttonAdd);

	choiceCounter = 0;

}


function addChoice(){
	
	var newId = "id".concat(choiceCounter); //id0, id1 itp
	var newName = "choice".concat(choiceCounter); 

	var choice = document.createElement("input");
	choice.setAttribute("id", newId); 
	choice.setAttribute("type", "text"); 
	choice.setAttribute("name", newName); 

	document.getElementById("newForm").appendChild(choice);
	choiceCounter = choiceCounter +1;
}


function submitNewForm(){
	var form = document.getElementById("newForm");
	form.submit();
}

function addAnswer1(){

	var x=document.getElementsByName("choice");

	var idConcated = "id".concat(x.length);
	alert(idConcated);
	var choice = document.createElement("input");
	choice.setAttribute("id", idConcated); 
	choice.setAttribute("type", "text"); 
	choice.setAttribute("name", "choice"); 
	document.getElementById("newForm1").appendChild(choice);
}

