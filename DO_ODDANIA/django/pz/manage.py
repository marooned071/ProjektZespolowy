'''
 Copyright 2014 Byliniak, Sliwka, Gambus, Celmer

 Licensed under the Surveys License, (the "License");
 you may not use this file except in compliance with the License.

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
'''

#!/usr/bin/env python
import os
import sys

if __name__ == "__main__":
    os.environ.setdefault("DJANGO_SETTINGS_MODULE", "pz.settings")

    from django.core.management import execute_from_command_line

    execute_from_command_line(sys.argv)
