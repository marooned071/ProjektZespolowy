

var choiceCounter = 0; //licznik odpowiedzi, zwieksza sie o jeden za kazdym razem gdy zostaje dodana odpowiedz do formularza 
// sluzy do inkrementowania id (id kazdej odpowiedzi musi byc inne aby bezproblemowa przeslac je POSTem)


function createForm(){

	//obiekt formularza
	var form = document.createElement("form");
	form.setAttribute("method", "POST"); 
	form.setAttribute("action", "newQuestion");
	form.setAttribute("id", "form");


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
	tdLabel.appendChild(document.createTextNode("Tresc pytania: "));

	form.appendChild(tr);

	//przycisk dodajacy miejsce na odpowiedz
	var buttonAddChoice = document.createElement("button");
	buttonAddChoice.setAttribute("onClick","addChoice()");
	var t=document.createTextNode("+ odpowiedz");
	buttonAddChoice.appendChild(t); 
	document.getElementById("buttons").appendChild(buttonAddChoice);


	//tabela zawierajaca miejsca na pisanie tresci odpowiedzi
	var table = document.createElement("table");
	table.setAttribute("id", "choiceTable");

	form.appendChild(table);

	//miejsce na buton wysylajacy formularz do bazy.  osoby div aby button zawsze byl w tym samym miejscu
	var buttonAddQuestionDiv = document.createElement("div");
	buttonAddQuestionDiv.setAttribute("id", "buttonAddQuestionDiv");
	form.appendChild(buttonAddQuestionDiv);


	var buttonAddQuestion = document.createElement("button");
	buttonAddQuestion.setAttribute("onClick","submitForm()");
	var t2=document.createTextNode("Dodaj Pytanie");
	buttonAddQuestion.appendChild(t2); 
	buttonAddQuestionDiv.appendChild(buttonAddQuestion);


	document.body.appendChild(form);
	choiceCounter = 0;

	//usuwamy przycisk tworzenia formularza, jesli stworzymy nowe pytanie robi sie syf z odpowiedziami trzeba to jakos ogarnac
	var buttonCreateForm = document.getElementById("buttonCreateForm");
	document.body.removeChild(buttonCreateForm);


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
	tdLabel.appendChild(document.createTextNode("odp ".concat(choiceCounter)));



	choice.setAttribute("id", newId); 
	choice.setAttribute("type", "text"); 
	choice.setAttribute("name", newName); 

	//document.getElementById("form").appendChild(choice);
	document.getElementById("choiceTable").appendChild(tr);




	choiceCounter = choiceCounter +1;
}


function submitForm(){
	var form = document.getElementById("form");
	form.submit();
}

