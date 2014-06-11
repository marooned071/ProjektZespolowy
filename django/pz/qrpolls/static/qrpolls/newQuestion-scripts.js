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

var choiceCounter = 0; //licznik odpowiedzi, zwieksza sie o jeden za kazdym razem gdy zostaje dodana odpowiedz do formularza 
// sluzy do inkrementowania id (id kazdej odpowiedzi musi byc inne aby bezproblemowa przeslac je POSTem)


function createForm(){

	//obiekt formularza
	var form = document.createElement("form");
	form.setAttribute("method", "POST"); 
	form.setAttribute("action", "newQuestion");
	form.setAttribute("id", "form");

	form.onsubmit = function() {
    	return false; //przeslaniem zajmujemy sie my (przed wyslaniem jest walidacja pol)
	}


	//token ktory jest potrzebny aby Django przyjal dane POST
	var CSRF_TOKEN = document.getElementById('csrf_token').value;
	var token = document.createElement("input");
	token.setAttribute("type", "hidden"); 
	token.setAttribute("name", "csrfmiddlewaretoken");
	token.setAttribute("value", CSRF_TOKEN);
	form.appendChild(token);


	// pytanie
    var questionText = document.createElement("input"); 
    questionText.setAttribute("id", "question"); 
	questionText.setAttribute("type", "text"); 
	questionText.setAttribute("name", "question"); 

	// wiersz tabeli ktory tworzy w pierwszej kolumnie jest opis pola, np "Tresc Pytania", a w drugiej odpowiedni komponent
	var tr = document.createElement("tr");
	var tdLabel = document.createElement("td");
	var tdInput = document.createElement("td");

	tr.appendChild(tdLabel);
	tr.appendChild(tdInput);
	
	tdInput.appendChild(questionText);
	tdLabel.appendChild(document.createTextNode("Question: "));

	form.appendChild(tr);

	var tr2 = document.createElement("tr");
	var tdLabel2 = document.createElement("td");
	var tdInput2 = document.createElement("td");

	tr2.appendChild(tdLabel2);
	tr2.appendChild(tdInput2);

	// pole na liczbe odpowiedzi:
	var choicesMax = document.createElement("select"); 
	choicesMax.setAttribute("id", "choicesMax"); 
	choicesMax.setAttribute("type", "text"); 
	choicesMax.setAttribute("name", "choicesMax"); 

	tdInput2.appendChild(choicesMax);
	tdLabel2.appendChild(document.createTextNode("Max Choices: "));

	form.appendChild(tr2);


	//przycisk dodajacy miejsce na odpowiedz
	var buttonAddChoice = document.createElement("button");
	buttonAddChoice.setAttribute("onClick","addChoice()");
	var t=document.createTextNode("Choice++");
	buttonAddChoice.appendChild(t); 
	document.getElementById("newQuestionButtons").appendChild(buttonAddChoice);


	//tabela zawierajaca miejsca na pisanie tresci odpowiedzi
	var table = document.createElement("table");
	table.setAttribute("id", "choiceTable");

	form.appendChild(table);

	//miejsce na buton wysylajacy formularz do bazy.  osoby div aby button zawsze byl w tym samym miejscu
	var buttonAddQuestionDiv = document.createElement("div");
	buttonAddQuestionDiv.setAttribute("id", "buttonAddQuestionDiv");
	form.appendChild(buttonAddQuestionDiv);


	//przycisk wywylajacy do abzy
	var buttonAddQuestion = document.createElement("button");
	buttonAddQuestion.setAttribute("onClick","submitForm()");
	var t2=document.createTextNode("Submit");
	buttonAddQuestion.appendChild(t2); 
	buttonAddQuestionDiv.appendChild(buttonAddQuestion);

	//przycisk chowajacy formularz tworzenia pytan
	var buttonHideForm = document.createElement("button");
	buttonHideForm.setAttribute("onClick","hideForm()");
	var t3=document.createTextNode("X");
	buttonHideForm.appendChild(t3); 
	buttonAddQuestionDiv.appendChild(buttonHideForm);



	document.getElementById("newQuestionDiv").appendChild(form);
	// document.body.appendChild(form);
	choiceCounter = 0;

	//usuwamy przycisk tworzenia formularza, jesli stworzymy nowe pytanie robi sie syf z odpowiedziami trzeba to jakos ogarnac
	// dla tego mozna tworzyc jedno pytanie na raz
	var buttonCreateForm = document.getElementById("buttonCreateForm").style.visibility="hidden";


}

//funckcja dodajaca odpowiedz
function addChoice(){
	
	var newId = "id".concat(choiceCounter); //id0, id1 itp
	var newName = "choice".concat(choiceCounter); 

	var choice = document.createElement("input");

	var tr = document.createElement("tr");
	var tdLabel = document.createElement("td");
	var tdInput = document.createElement("td");

	tr.appendChild(tdLabel);
	tr.appendChild(tdInput);
	
	tdInput.appendChild(choice);
	tdLabel.appendChild(document.createTextNode("Choice  ".concat(choiceCounter+1))); //Choice 1, Choice 2 itp

	choice.setAttribute("id", newId); 
	choice.setAttribute("type", "text"); 
	choice.setAttribute("name", newName); 

	document.getElementById("choiceTable").appendChild(tr);
	choiceCounter = choiceCounter +1;

	var option = document.createElement("option"); //dodanie pola wyboru do comboboxa z wyborem iloscy pytan
	option.text = choiceCounter;
	document.getElementById("choicesMax").add(option);


}

//walidacja pol
function isValidate(){
	var questionValue = document.getElementById("question").value;
	if (questionValue == ""){
		alert("No question.");
		return false;
	}
	return true;
}

//funkcja wylowywana po nacisnieciu przycisku Sumbit, odpowiada za przeslanie danych
function submitForm(){
	if(isValidate()){
		var form = document.getElementById("form");
		form.submit();
	}
	return false;
}

//funkcja wylosywana po nacisnieciu przycisku "X" chowa caly formularz
function hideForm(){
	var newQuestionDiv = document.getElementById("newQuestionDiv");
	while (newQuestionDiv.firstChild) {
    	newQuestionDiv.removeChild(newQuestionDiv.firstChild);
	}
	var newQuestionButtons = document.getElementById("newQuestionButtons");
	while (newQuestionButtons.firstChild) {
    	newQuestionButtons.removeChild(newQuestionButtons.firstChild);
	}
	var buttonCreateForm = document.getElementById("buttonCreateForm").style.visibility="visible";
}
