#!/usr/bin/env python

import socket


descriptor=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
descriptor.connect( ("127.0.0.1",  9876))
descriptor.send(b'+\n')

descriptor.send(b'42\n')

descriptor.send(b'42\n')

descriptor.close()