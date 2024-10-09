from flask import Flask, request, jsonify
import os
from deepface import DeepFace
from flask_cors import CORS
import mysql.connector
import json
app = Flask(__name__)
CORS(app) 

DB_HOST = 'localhost'
DB_NAME = 'attendance'
DB_USER = 'root'
DB_PASSWORD = '12345'

def get_image_by_email(email):
    try:
        conn = mysql.connector.connect(host=DB_HOST, database=DB_NAME, user=DB_USER, password=DB_PASSWORD)
        cursor = conn.cursor()
        sql_query = "SELECT img FROM student WHERE email = %s"
        cursor.execute(sql_query, (email,))
        result = cursor.fetchone()
        cursor.close()
        conn.close()
        if result:
            return result[0]
    except mysql.connector.Error as err:
        pass
    return None

@app.route('/compare', methods=['POST'])
def compare_faces():
    if 'email' not in request.form:
        return 'No email part'
    email = request.form['email']
    if 'file' not in request.files:
        return 'No file part'
    file = request.files['file']
    
    
    
    if file.filename == '':
        return 'No selected file'
    current_dir = os.path.dirname(os.path.realpath(__file__))
    temp_image_path = os.path.join(current_dir, 'temp_image.jpg')

    # Save the file to temp_image_path
    file.save(temp_image_path)

    db_image = get_image_by_email(email)
    if db_image is None:
        return json.dumps({'message': 'No image found for the given email'})

    result = DeepFace.verify(temp_image_path, db_image, enforce_detection=False)
    os.remove(temp_image_path)

    return jsonify({'verified': result["verified"], 'distance': result["distance"]})

if __name__ == '__main__':
    app.run(debug=True, port=5001)