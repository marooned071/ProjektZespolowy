from django.template import RequestContext, loader
from django.shortcuts import get_object_or_404, render, Http404
from django.http import HttpResponseRedirect, HttpResponse
from django.core.urlresolvers import reverse
import time

import hashlib
import json

from qrpolls.models import QRPoll
from qrpolls.forms import NewMeetingForm


def index(request):
    n = NewMeetingForm
    return render(request, 'qrpolls/index.html',{'form': n})

def meeting(request, hash_id):

    try:
        poll = QRPoll.objects.get(hash_id=hash_id) # pobieramy hash z bazy, jesli go nie ma w bazie bedzie 404
    except QRPoll.DoesNotExist:
        raise Http404

    path = request.META['REMOTE_ADDR']+request.path
    return render(request, 'qrpolls/meeting.html', {'poll': poll, 'url' : path})

def create(request):
    m = hashlib.md5()

    subject = request.POST['subject']
    room = request.POST['room'] 
    start_date = request.POST['start_date']


    millis = int(round(time.time() * 1000)) # czas ktory mamy teraz w miliseundach (sol do hasha)
    s = str(subject)+str(room)+str(start_date)+str(millis)
    hash_id = hashlib.md5(s.encode()).hexdigest()

    poll = QRPoll(hash_id=hash_id, start_date = start_date, room = room, subject = subject);

    poll.save()

    return HttpResponseRedirect(reverse('qrpolls:meeting', args=(hash_id,)))


def api(request, hash_id, question):
    try:
        poll = QRPoll.objects.get(hash_id=hash_id) # pobieramy hash z bazy, jesli go nie ma w bazie bedzie 404
    except QRPoll.DoesNotExist: 
        return HttpResponse("Poll doesn't exist.")

    if question == "info":
        data = [ {'info': {
                'hash_id' : poll.hash_id,
                'start_date' : poll.start_date,
                'room' : poll.room,
                'subject' : poll.subject,
        }}]
        data_string = json.dumps(data)
        return HttpResponse(repr(data_string))



    return HttpResponse('nic')
    

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
