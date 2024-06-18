import requests
import random
import string
from faker import Faker

fake = Faker()

BASE_URL = 'http://127.0.0.1:8080/api'

def create_random_publisher():
    url = BASE_URL + '/publishers'
    data = {
        "name": fake.company(),
        "location": fake.city()
    }
    response = requests.post(url, json=data)
    if response.status_code == 201:
        print("Publisher created successfully")
        print("Response JSON:", response.json())
    else:
        print("Failed to create publisher")
        print("Status code:", response.status_code)
        print("Response text:", response.text)

def create_random_affiliation():
    url = BASE_URL + '/affiliations'
    data = {
        "name": fake.company(),
        "parent": create_random_affiliation() if random.random() > 0.95 else None  
    }
    response = requests.post(url, json=data)
    if response.status_code == 201:
        print("Affiliation created successfully")
        print("Response JSON:", response.json())
    else:
        print("Failed to create affiliation")
        print("Status code:", response.status_code)
        print("Response text:", response.text)
    affiliation_id = response.json().get('id') 
    return affiliation_id

def create_random_author():
    url = BASE_URL + '/authors'
    affiliation_id = create_random_affiliation() 
    data = {
        "name": fake.first_name(),
        "surname": fake.last_name(),
        "affiliation": affiliation_id 
    }
    response = requests.post(url, json=data)
    if response.status_code == 201:
        print("Author created successfully")
        print("Response JSON:", response.json())
    else:
        print("Failed to create author")
        print("Status code:", response.status_code)
        print("Response text:", response.text)


def create_random_journal():
    url = BASE_URL + '/journals'
    data = {
        "baseScore": random.randint(1, 10),
        "title": fake.sentence(),
        "publisher": create_random_publisher(),
        "issn": fake.isbn13()
    }
    response = requests.post(url, json=data)
    if response.status_code == 201:
        print("Journal created successfully")
        print("Response JSON:", response.json())
    else:
        print("Failed to create journal")
        print("Status code:", response.status_code)
        print("Response text:", response.text)


def create_random_book():
    url = BASE_URL + '/books'
    data = {
        "isbn": fake.isbn13(),
        "year": random.randint(1950, 2023),
        "baseScore": random.randint(1, 10),
        "title": fake.sentence(),
        "publisher": create_random_publisher(),
        "editor": create_random_author()
    }
    response = requests.post(url, json=data)
    if response.status_code == 201:
        print("Book created successfully")
        print("Response JSON:", response.json())
    else:
        print("Failed to create book")
        print("Status code:", response.status_code)
        print("Response text:", response.text)



def create_random_chapter():
    url = BASE_URL + '/chapters'
    data = {
        "score": random.randint(1, 100),
        "collection": fake.word(),
        "title": fake.sentence(),
        "authors": [create_random_author(), create_random_author()],
        "book": create_random_book()
    }
    response = requests.post(url, json=data)
    if response.status_code == 201:
        print("Chapter created successfully")
        print("Response JSON:", response.json())
    else:
        print("Failed to create chapter")
        print("Status code:", response.status_code)
        print("Response text:", response.text)

def create_random_article():
    url = BASE_URL + '/articles'
    data = {
        "title": fake.sentence(),
        "collection": fake.word(),
        "score": random.randint(1, 100),
        "year": random.randint(1950, 2023),
        "vol": random.randint(1, 10),
        "no": random.randint(1, 10),
        "articleNo": random.randint(1, 10),
        "journal": create_random_journal(),
        "authors": [create_random_author(), create_random_author()]
    }
    response = requests.post(url, json=data)
    if response.status_code == 201:
        print("Article created successfully")
        print("Response JSON:", response.json())
    else:
        print("Failed to create article")
        print("Status code:", response.status_code)
        print("Response text:", response.text)


def add_data():
    for _ in range(14):
        create_random_affiliation()
    for _ in range(15):
        create_random_author()
    for _ in range(14):
        create_random_publisher()
    for _ in range(14):
        create_random_journal()
    for _ in range(14):
        create_random_book()
    for _ in range(14):
        create_random_chapter()
    for _ in range(15):
        create_random_article()

def fetch_article_ids():
    url = BASE_URL + '/articles'
    response = requests.get(url)
    if response.status_code == 200:
        articles = response.json()['_embedded']['articles']
        return [article['_links']['self']['href'] for article in articles]
    else:
        print("Status code:", response.status_code)
        print("Response text:", response.text)
        return []

def add_year_to_article(article_url):
    data = {
        "year": random.randint(1950, 2023)
    }
    response = requests.patch(article_url, json=data)
    if response.status_code == 200:
        print("Response JSON:", response.json())
    else:
        print("Status code:", response.status_code)
        print("Response text:", response.text)

if __name__ == "__main__":
    add_data()

    #article_urls = fetch_article_ids()  # dodanie brakujących year do artykulków
    #for article_url in article_urls:
     #   add_year_to_article(article_url)
   #print(fake.sentence())
  # print(fake.word())
   #print(fake.city())
  # print(fake.first_name())
  # print(fake.last_name())
  # print(fake.company())
   #print(fake.isbn13())