//const environment = process.env.NODE_ENV;
export const FETCH_ASSESSMENT_SUCCESS = 'FETCH_ASSESSMENT_SUCCESS';
export const FETCH_ASSESSMENT_FAILURE = 'FETCH_ASSESSMENT_FAILURE';

export const fetchAssessmentSuccess = (result) => ({
    type: FETCH_ASSESSMENT_SUCCESS,
    payload: result,
});
export const fetchAssessmentFailure = (error) => ({
    type: FETCH_ASSESSMENT_FAILURE,
    payload: error,
});
export default function getAssesmentResults(result) {
    return function (dispatch) {
        dispatch(fetchAssessmentSuccess(result));
    }
}