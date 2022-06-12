
export const AppConfig = {
  SERVICE_URL : {
    AUTHENTICATE_URL : 'api/authenticate',
    REFRESH_TOKEN_URL : 'api/authenticate/refresh',
    LOGOUT_URL : 'api/logout',
    GET_USER_PROFILE_URL : '/api/user/profile',
    GET_CHALLENGE_URL : '/api/challenge/list',
    GET_CHALLENGE_LIST_URL : '/api/challenge/manage/list',
    CREATE_CHALLENGE_URL : '/api/challenge',
    GET_CHALLENGE_DETAILS_URL : '/api/challenge/:challengeId',
    UPDATE_CHALLENGE_URL : '/api/challenge/:challengeId',
    DELETE_CHALLENGE_URL : '/api/challenge/:challengeId',
    PUBLISH_CHALLENGE_URL : '/api/challenge/:challengeId/publish',
    STOP_CHALLENGE_URL : '/api/challenge/:challengeId/stop',
    REGISTER_CHALLENGE_URL : '/api/challenge/:challengeId/register',
    CREATE_CHALLENGE_INTANCE_URL : '/api/challenge/instance',    
    GET_CHALLENGE_INTANCE_URL : '/api/challenge/instance/:challengeInstanceId',
    UPDATE_CHALLENGE_INTANCE_URL : '/api/challenge/instance/:challengeInstanceId',
    DELETE_CHALLENGE_INTANCE_URL : '/api/challenge/instance/:challengeInstanceId',
    PUBLISH_CHALLENGE_INTANCE_URL : '/api/challenge/instance/:challengeInstanceId/publish',
    STOP_CHALLENGE_INTANCE_URL : '/api/challenge/instance/:challengeInstanceId/stop',    
    GET_CHALLENGE_INTANCE_SUBMISSIONS_URL : '/api/challenge/instance/:challengeInstanceId/submissions',    
    GET_SUBMITTED_PROBLEMS_URL : '/api/challenge/instance/submission/:challengeInstanceSubmissionId/problems',    
    UPDATE_PROBLEM_SUBMISSION_URL : '/api/challenge/instance/submission/problem/:problemSubmissionId',    
    GET_CHALLENGE_INSTANCE_LIST_URL : '/api/challenge/:challengeId/instance/list',    
    GET_LIVE_CHALLENGE_DETAILS_URL : '/api/challenge/live/:challengeInstanceId',    
    START_CHALLENGE_URL : '/api/challenge/instance/:challengeInstanceId/start',
    SUBMIT_CHALLENGE_URL : '/api/challenge/instance/:challengeInstanceId/submit',
    GET_INSTANCE_PROBLEMS_URL : '/api/challenge/instance/:challengeInstanceId/problems',
    COMPILE_SOLUTION_URL : '/api/challenge/solution/compile',
    RUN_ALL_TEST_CASES_URL : '/api/challenge/solution/run',
    SUBMIT_SOLUTION_URL : '/api/challenge/solution/submit',
    SAVE_SOLUTION_URL : '/api/challenge/solution/save',
    GET_PROBLEMS_URL : '/api/problem/:challengeInstanceId/list',
    CREATE_PROBLEM_URL : '/api/problem',
    UPDATE_PROBLEM_URL : '/api/problem/:problemId',
    DELETE_PROBLEM_URL : '/api/problem/:problemId',
    GET_PROBLEM_URL : '/api/problem/:problemId',
    GET_LANGUAGES_URL : '/api/problem/languages'
  }

}
