from django.template import RequestContext, loader
from django.shortcuts import get_object_or_404, render, Http404
from django.http import HttpResponseRedirect, HttpResponse
from django.core.urlresolvers import reverse
from django.core import serializers
import time

import hashlib
import json

from qrpolls.models import QRPoll, Question, Choice, Vote
from qrpolls.forms import NewMeetingForm


# Metoda wyswietlajaca strone glowna
def index(request):
    n = NewMeetingForm
    poll_list = QRPoll.objects.all()
    return render(request, 'qrpolls/index.html',{'form': n, 'poll_list': poll_list})

# Metoda wyswietlajaca strone spotkania o id : hash_id
def meeting(request, hash_id):

    try:
        poll = QRPoll.objects.get(hash_id=hash_id) # pobieramy hash z bazy, jesli go nie ma w bazie bedzie 404
    except QRPoll.DoesNotExist:
        raise Http404





    question_list = Question.objects.filter(poll=poll)
    #rating_list_anwers=Question.objects.filter(poll=poll,isRating=True)
    ratingQuestion = Question.objects.get(poll=poll, isRating=True) #pytanie o ocene spotkania (jest tylko jedno dlatego mozemy zrobic get)
    rating_list_choices = Choice.objects.filter(question=ratingQuestion) #lista odpowiedzi na pytanie ratingowe
    rating=0 #rating spotkania
    allVotersCount=0 #liczba wszystkich glosujacych
    for rate_choice in rating_list_choices: #dla kazdej odpowiedzi 0.0 ,0.5 ,1.5 ..
        voters = Vote.objects.filter(choice=rate_choice) #glosujacy na ta ocene
        votersCount=int(voters.count()) #liczba glosujacych na ta ocene
        allVotersCount+=votersCount
        rating+=float(rate_choice.choice_text)*votersCount

    if allVotersCount!=0: # gdyby nikt nie ocenil spotkania  
        rating/=allVotersCount

    rating = round(rating,2)

    # slownik {tekst_pytania : slownik {tekstOdpowiedzi : liczba glosow }}
    questionChoiceVotesDic={}



    for question in question_list:
        if question.isRating == True:
            continue
        choices_list = Choice.objects.filter(question=question)
        newDic = []
        newDic.append(["Siema", "Nara"]);
        votesCounter=0
        for choice in choices_list:
           # newDic[choice.choice_text] = choice.vote_set.count()
            votesCounter+=choice.votes
            newDic.append([choice.choice_text, choice.vote_set.count()])
        if votesCounter == 0:
            newDic.append(["No Votes", 1]);




 


    path = request.META['SERVER_NAME']+request.path

    data = {'poll': poll, 'url' : path, 'question_list' : question_list, 'rating':rating, 'allVotersCount':allVotersCount, 'questionChoiceVotesDic':questionChoiceVotesDic};
    return render(request, 'qrpolls/meeting.html', data)

def meetingTest(request, hash_id):
    try:
        poll = QRPoll.objects.get(hash_id=hash_id) # pobieramy hash z bazy, jesli go nie ma w bazie bedzie 404
    except QRPoll.DoesNotExist:
        raise Http404



    question_list = Question.objects.filter(poll=poll)
    #rating_list_anwers=Question.objects.filter(poll=poll,isRating=True)
    ratingQuestion = Question.objects.get(poll=poll, isRating=True) #pytanie o ocene spotkania (jest tylko jedno dlatego mozemy zrobic get)
    rating_list_choices = Choice.objects.filter(question=ratingQuestion) #lista odpowiedzi na pytanie ratingowe
    rating=0 #rating spotkania
    allVotersCount=0 #liczba wszystkich glosujacych
    for rate_choice in rating_list_choices: #dla kazdej odpowiedzi 0.0 ,0.5 ,1.5 ..
        voters = Vote.objects.filter(choice=rate_choice) #glosujacy na ta ocene
        votersCount=int(voters.count()) #liczba glosujacych na ta ocene
        allVotersCount+=votersCount
        rating+=float(rate_choice.choice_text)*votersCount

    if allVotersCount!=0: # gdyby nikt nie ocenil spotkania 
        rating/=allVotersCount

    rating = round(rating,2)

    questionChoiceVotesDic={}

    # slownik {tekst_pytania : slownik {tekstOdpowiedzi : liczba glosow }}

    for question in question_list:
        if question.isRating == True:
            continue
        choices_list = Choice.objects.filter(question=question)
        newDic = []
        newDic.append(["Siema", "Nara"]);
        votesCounter=0
        for choice in choices_list:
           # newDic[choice.choice_text] = choice.vote_set.count()
            votesCounter+=choice.votes
            newDic.append([choice.choice_text, choice.vote_set.count()])
        if votesCounter == 0:
            newDic.append(["No Votes", 1]);


        questionChoiceVotesDic[question.question_text] =json.dumps(newDic)



    path = request.META['SERVER_NAME']+request.path
    data = {'poll': poll, 'url' : path, 'question_list' : question_list, 'rating':rating, 'allVotersCount':allVotersCount, 'questionChoiceVotesDic' : questionChoiceVotesDic};
    # return render(request, 'qrpolls/meeting.html', {'poll': poll, 'url' : path, 'question_list' : question_list, 'rating':rating, 'allVotersCount':allVotersCount })
    # return render_to_response('qrpolls/meeting.html', data, context_instance = RequestContext(request))
    return render(request, 'qrpolls/meeting_questions.html', data)


# Metoda tworzaca nowe spotkanie
def create(request):
    m = hashlib.md5()

    subject = request.POST['subject']
    room = request.POST['room'] 
    start_date = request.POST['start_date']


    millis = int(round(time.time() * 1000)) # czas ktory mamy teraz w miliseundach (sol do hasha)
    s = str(subject)+str(room)+str(start_date)+str(millis)
    hash_id = hashlib.md5(s.encode()).hexdigest()

    poll = QRPoll(hash_id=hash_id, start_date = start_date, room = room, subject = subject, version = 1);
    poll.save()


    question = Question(poll=poll, question_text = "Jak sie podoba?") # przykladowa ankieta
    question.save()

    choice = Choice(question=question, choice_text = "Fajnie")
    choice.save()
    choice = Choice(question=question, choice_text = "Srednio")
    choice.save()
    choice = Choice(question=question, choice_text = "Nudy")
    choice.save()


    question = Question(poll=poll, question_text = "Raiting", question_choices_max=1, isRating=True) # przykladowa ankieta
    question.save()

    choice = Choice(question=question, choice_text = "0")
    choice.save()
    choice = Choice(question=question, choice_text = "0.5")
    choice.save()
    choice = Choice(question=question, choice_text = "1.0")
    choice.save()
    choice = Choice(question=question, choice_text = "1.5")
    choice.save()
    choice = Choice(question=question, choice_text = "2.0")
    choice.save()
    choice = Choice(question=question, choice_text = "2.5")
    choice.save()
    choice = Choice(question=question, choice_text = "3.0")
    choice.save()
    choice = Choice(question=question, choice_text = "3.5")
    choice.save()
    choice = Choice(question=question, choice_text = "4.0")
    choice.save()
    choice = Choice(question=question, choice_text = "4.5")
    choice.save()
    choice = Choice(question=question, choice_text = "5.0")
    choice.save()


    return HttpResponseRedirect(reverse('qrpolls:meeting', args=(hash_id,)))

# Metoda dodajaca nowe pytanie do spotkania o id: hash_id
# Dane dotyczace nowego pytania zawarte sa w POST
def newQuestion(request,hash_id):
    questionText = request.POST['question']
    question_choices_max = request.POST['choicesMax']


    l = len(request.POST.keys()) # l - wielkosc slownika z post, powinny znajdowac się tam: dict_keys(['question', 'csrfmiddlewaretoken', 'choice0'])

    choices = []
    for currId in range(l-3): #poza polami wyboru w slowniku znajduja sie trzy inne pola (question, choicesMax, oraz csrfmiddlewaretoken), dlatego -3
        s = 'choice'+str(currId)
        choices.append(request.POST[s])

    try:
        poll = QRPoll.objects.get(hash_id=hash_id) # pobieramy hash z bazy, jesli go nie ma w bazie bedzie 404
    except QRPoll.DoesNotExist: 
        raise Http404

    poll.version+=1;
    poll.save()

    question = Question(poll=poll, question_text = questionText, question_choices_max= question_choices_max)
    question.save()

    for c in choices:
        choice = Choice(question=question, choice_text = c)
        choice.save()

    return HttpResponseRedirect(reverse('qrpolls:meeting', args=(hash_id,)))


# Metoda odslugujaca zapytania do api.
# qrpolls/meeting/<hash_id>/api/info/ - zwraca informacje na temat spotkania
# qrpolls/meeting/<hash_id>/api/version/ - zwraca wersje spotkania
# qrpolls/meeting/<hash_id>/api/questions/ - zwraca pytania ktore sa przypisane do spotkania
# qrpolls/meeting/<hash_id>/api/choices/ - zwraca mozliwe odpowiedzi do tych pytan ktore sa przypisane do spotkania.
def api(request, hash_id, question):
    try:
        poll = QRPoll.objects.get(hash_id=hash_id) # pobieramy hash z bazy, jesli go nie ma w bazie bedzie 404
    except QRPoll.DoesNotExist: 
        raise Http404

    if question == "info":  #informacja o spotkaniu
        data = [ {'info': {
                'hash_id' : poll.hash_id,
                'start_date' : poll.start_date,
                'room' : poll.room,
                'subject' : poll.subject,
        }}]
        data_string = json.dumps(data)
        return HttpResponse(data_string)

        # [{"info": {"room": "11", "subject": "777", "hash_id": "62c694079277679c1e7859de489a5e3c", "start_date": "44"}}]
        #'[{"info": {"start_date": "44", "subject": "777", "room": "11", "hash_id": "62c694079277679c1e7859de489a5e3c"}}]'



    elif question == "version": #wersja spotkania, po dodaniu ankiety version++
        data = [ {'version': poll.version}]
        data_string = json.dumps(data)
        return HttpResponse(data_string)

   
    elif question == "questions": #pytania w tym spotkaniu
        data = serializers.serialize("json", Question.objects.filter(poll=poll))
        return HttpResponse(data)

    elif question == "choices": #odpowiedzi na pytania z tego spotkania
        question_list = Question.objects.filter(poll=poll)
        all_objects = []
        for question in question_list:
            all_objects.extend(list(Choice.objects.filter(question=question)))

        data = serializers.serialize('json', all_objects)

        return HttpResponse(data)


    raise Http404


# Metoda obslugujaca glosowanie bez rozrozniania glosujacych - kazdy moze wiele razy glosowac na ta sama odpowedz
# Tak po prawdzie do glosowanie nie jest potrzebny hash_id, ale przydaje aby po zaglosowaniu wyswietlic strone spotkania 
# w celach debug - w przyszlosci nie bedzie takiej potrzeby
def api_vote(request, hash_id, choice_id):

    choice = Choice.objects.get(pk=choice_id)
    choice.votes+=1
    choice.save()


    return HttpResponseRedirect(reverse('qrpolls:meeting', args=(hash_id,)))

# Metoda obslugujaca glosowanie z rozroznieniem glosujacych
# Tak po prawdzie do glosowanie nie jest potrzebny hash_id, ale przydaje aby po zaglosowaniu wyswietlic strone spotkania 
# w celach debug - w przyszlosci nie bedzie takiej potrzeby
def api_vote_voter(request, hash_id, voter_id, choice_ids):
    # choice_ids - kolejne id odzielane sa przecinkami

    id_list = choice_ids.split(',') # lista id odpowiedzi na ktore zaglosowano

    first_choice = Choice.objects.get(pk=id_list[0]) # pierwsza wybor z listy 
    question = first_choice.question
    question_choices_max = question.question_choices_max # na ile odpowiedzi mozna maksymalnie zaglosowac ?

    # usuwanie poprzednich odpowiedzi glosujacego o id voter_id do danego pytania
    Vote.objects.filter(voter_id=voter_id,choice__question=question).delete() # __ w choice__question oznacza podazanie za foregin key



    if question_choices_max == 0: # pytanie otwarte
        return HttpResponse("qcm == 0 jeszcze nie zaimplementowano")


    elif question_choices_max == 1:
        if len(id_list) != 1:
            data = [ {'voteInfo': {
                    'error' : "true",
                    'description' : "Pytanie jednkrotnego wyboru, a liczba odpowiedzi >1",
            }}]
            data_string = json.dumps(data)
            return HttpResponse(data_string)

        choice =  Choice.objects.get(pk=id_list[0]);
        choice.votes+=1;
        choice.save() 


        vote = Vote(choice=first_choice,voter_id=voter_id)
        vote.save()   


    elif question_choices_max >= 2:
        if len(id_list) > question_choices_max:
            data = [ {'voteInfo': {
                    'error' : "true",
                    'description' : "Pytanie wielonrotnego wyboru, lista odpowiedzi > mozliwe odpowiedzi",
            }}]
            data_string = json.dumps(data)
            return HttpResponse(data_string)

        for id in id_list: 
            choice =  Choice.objects.get(pk=id) 
            choice.votes+=1;
            choice.save()
            vote = Vote(choice=choice,voter_id=voter_id)
            vote.save()





    #data = question_choices_max
   # data = id_list

    data = [ {'voteInfo': {
                    'error' : "false",
         }}]
    data_string = json.dumps(data)
    return HttpResponse(data_string)

    #return HttpResponseRedirect(reverse('qrpolls:meeting', args=(hash_id,)))
   # return HttpResponseRedirect(reverse('qrpolls:meeting', args=(hash_id,)))




