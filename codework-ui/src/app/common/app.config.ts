
export const AppConfig = {
  SERVICE_URL : {
    AUTHENTICATE_URL : 'api/authenticate',
    LOGOUT_URL : 'api/logout',
    GET_USER_PROFILE_URL : '/api/user/profile',
    GET_CHALLENGE_URL : '/api/challenge/list',
    GET_CHALLENGE_LIST_URL : '/api/challenge/manage/list',
    START_CHALLENGE_INTANCE_URL : '/api/challenge/instance/:challengeInstanceId/start',
    STOP_CHALLENGE_INTANCE_URL : '/api/challenge/instance/:challengeInstanceId/stop',
    GET_CHALLENGE_INSTANCE_LIST_URL : '/api/challenge/:challengeId/instance/list',
    GET_CHALLENGE_DETAILS_URL : '/api/challenge/:challengeId',
    GET_LIVE_CHALLENGE_DETAILS_URL : '/api/challenge/live/:challengeInstanceId',
    REGISTER_CHALLENGE_URL : '/api/challenge/:challengeId/register',
    START_CHALLENGE_URL : '/api/challenge/:challengeInstanceId/start',
    SUBMIT_CHALLENGE_URL : '/api/challenge/:challengeInstanceId/submit',
    GET_INSTANCE_PROBLEMS_URL : '/api/challenge/instance/:challengeInstanceId/problems',
    COMPILE_SOLUTION_URL : '/api/challenge/solution/compile',
    RUN_ALL_TEST_CASES_URL : '/api/challenge/solution/run',
    SUBMIT_SOLUTION_URL : '/api/challenge/solution/submit',
    SAVE_SOLUTION_URL : '/api/challenge/solution/save',
    GET_PROBLEMS_URL : '/api/problem/:challengeInstanceId/list',
    CREATE_PROBLEM_URL : '/api/problem',
    DELETE_PROBLEM_URL : '/api/problem/:problemId',
    GET_PROBLEM_URL : '/api/problem/:problemId',
    GET_LANGUAGES_URL : '/api/problem/languages'
  }

}
