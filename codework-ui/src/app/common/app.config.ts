
export const AppConfig = {
  SERVICE_URL : {
    GET_CHALLENGE_URL : '/api/challenge/list',
    GET_CHALLENGE_DETAILS_URL : '/api/challenge/:challengeId',
    GET_LIVE_CHALLENGE_DETAILS_URL : '/api/challenge/live/:challengeInstanceId',
    REGISTER_CHALLENGE_URL : '/api/challenge/:challengeId/register',
    START_CHALLENGE_URL : '/api/challenge/:challengeInstanceId/start',
    SUBMIT_CHALLENGE_URL : '/api/challenge/:challengeInstanceId/submit',
    GET_PROBLEMS_URL : '/api/challenge/instance/:challengeInstanceId/problems',
    COMPILE_SOLUTION_URL : '/api/challenge/solution/compile',
    RUN_ALL_TEST_CASES_URL : '/api/challenge/solution/run',
    SUBMIT_SOLUTION_URL : '/api/challenge/solution/submit',
    SAVE_SOLUTION_URL : '/api/challenge/solution/save',
  }

}
