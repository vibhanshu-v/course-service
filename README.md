# course-service
This is sample course service

//Add a course
curl -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -i 'http://localhost:8082/services/api/courses/create' --data '{"name":"Business Administration"}'
curl -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -i 'http://localhost:8082/services/api/courses/create' --data '{"name":"Data Analysis"}'
curl -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -i 'http://localhost:8082/services/api/courses/create' --data '{"name":"Machine Learning"}'


// Get All courses
curl -X GET -H 'Content-Type: application/json' -H 'Accept: application/json' -i 'http://localhost:8082/services/api/courses'

// Get course By id
curl -X GET -H 'Content-Type: application/json' -H 'Accept: application/json' -i 'http://localhost:8082/services/api/course/1'

// Get course By name
curl -X GET -H 'Content-Type: application/json' -H 'Accept: application/json' -i 'http://localhost.apple.com:8082/services/api/course/Business%20Administration' --data '{"name"}'

//Remove course
curl -X GET -H 'Content-Type: application/json' -H 'Accept: application/json' -i 'http://localhost:8082/services/api/course/remove/1'
