from django.template import RequestContext, loader
from django.shortcuts import get_object_or_404, render, Http404
from django.http import HttpResponseRedirect, HttpResponse
from django.core.urlresolvers import reverse
from django.core import serializers
import time

import hashlib
import json

from qrpolls.models import QRPoll, Question, Choice
from qrpolls.forms import NewMeetingForm


def index(request):
    n = NewMeetingForm
    poll_list = QRPoll.objects.all()
    return render(request, 'qrpolls/index.html',{'form': n, 'poll_list': poll_list})

def meeting(request, hash_id):

    try:
        poll = QRPoll.objects.get(hash_id=hash_id) # pobieramy hash z bazy, jesli go nie ma w bazie bedzie 404
    except QRPoll.DoesNotExist:
        raise Http404

    question_list = Question.objects.filter(poll=poll)
    path = request.META['REMOTE_ADDR']+request.path
    return render(request, 'qrpolls/meeting.html', {'poll': poll, 'url' : path, 'question_list' : question_list })

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

    return HttpResponseRedirect(reverse('qrpolls:meeting', args=(hash_id,)))


def newQuestion(request,hash_id):
    questionText = request.POST['question']


    l = len(request.POST.keys()) # l - wielkosc slownika z post, powinny znajdowac się tam: dict_keys(['question', 'csrfmiddlewaretoken', 'choice0'])

    choices = []
    for currId in range(l-2): #poza polami wyboru w slowniku znajduja sie dwa inne pola, dlatego -2
        s = 'choice'+str(currId)
        choices.append(request.POST[s])

    try:
        poll = QRPoll.objects.get(hash_id=hash_id) # pobieramy hash z bazy, jesli go nie ma w bazie bedzie 404
    except QRPoll.DoesNotExist: 
        raise Http404

    poll.version+=1;
    poll.save()

    question = Question(poll=poll, question_text = questionText)
    question.save()

    for c in choices:
        choice = Choice(question=question, choice_text = c)
        choice.save()

    return HttpResponseRedirect(reverse('qrpolls:meeting', args=(hash_id,)))



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


def api_vote(request, hash_id, choice_id):

    choice = Choice.objects.get(pk=choice_id)
    choice.votes+=1
    choice.save()


    return HttpResponseRedirect(reverse('qrpolls:meeting', args=(hash_id,)))


 # {"menu": {
 #   "id": "file",
 #   "value": "File",
 #   "popup": {
 #     "menuitem": [
 #       {"value": "New", "onclick": "CreateNewDoc()"},
 #       {"value": "Open", "onclick": "OpenDoc()"},
 #       {"value": "Close", "onclick": "CloseDoc()"}
 #     ]
 #   }
 # }}
