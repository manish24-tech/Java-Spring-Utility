print('Start #################################################################');
// Create the first database
db = db.getSiblingDB('fourbench_user_management');
db.createCollection('users');

db.createCollection('registration_code');
db.registration_code.insertOne({  _id: ObjectId("64577eb8307a5218b891d9d8"), code: 12345678})

db.createCollection('roles');
db.roles.insertOne({  _id: ObjectId("64577eb8307a5218b891d9d8"), name: 'IT_ADMIN'});
db.roles.insertOne({  _id: ObjectId("64577eb8307a5218b891d9d9"), name: 'PRINCIPAL'});
db.roles.insertOne({  _id: ObjectId("64577eb8307a5218b891d9da"), name: 'TEACHER'});
db.roles.insertOne({  _id: ObjectId("64577eb8307a5218b891d9db"), name: 'PARENTS'});

// Create the second database
db = db.getSiblingDB('fourbench_collaboration_management');
db.createCollection('posts');
db.createCollection('comments');

// Create the third database
db = db.getSiblingDB('fourbench_school_management');
db.createCollection('schools');
db.createCollection('students');
db.createCollection('teachers');
db.createCollection('parents');
db.createCollection('principals')

print('END #################################################################');