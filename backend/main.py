"""This simple CRUD application performs the following operations sequentially:
    1. Creates 100 new accounts with randomly generated IDs and randomly-computed balance amounts.
    2. Chooses two accounts at random and takes half of the money from the first and deposits it
     into the second.
    3. Chooses five accounts at random and deletes them.
"""

from models import Account
from argparse import ArgumentParser
from math import floor
import os
import random
import uuid
import urllib.parse

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy_cockroachdb import run_transaction
from dotenv import dotenv_values

config = dotenv_values(".env")


def create_account(session):

    print("Creating new accounts...")
    user_id = uuid.uuid4()
    cash = 0
    bounties = []
    print("Process Finished")
    session.add(Account(id=user_id, cash=cash))


if __name__ == '__main__':

    conn_string = config["DB_URI"]

    try:

        db_uri = urllib.parse.unquote(conn_string)

        psycopg_uri = db_uri.replace(
            'postgresql://', 'cockroachdb://').replace(
                'postgres://', 'cockroachdb://').replace(
                    '26257?', '26257/bank?')

        engine = create_engine(psycopg_uri)
    except Exception as e:
        print('Failed to connect to database.')
        print('{0}'.format(e))

    seen_account_ids = []

    run_transaction(sessionmaker(bind=engine),
                    lambda s: create_account(s))
