import {
    FETCH_CHANGEASSESSMENTPAGES_SUCCESS,
    FETCH_CHANGEASSESSMENTPAGES_FAILURE
} from '../actions/changeAssessmentPagesAction';
const defaultState = true;
function reducer(state = defaultState, { type, payload }) {
    switch (type) {
        case FETCH_CHANGEASSESSMENTPAGES_SUCCESS:
            return payload;
        case FETCH_CHANGEASSESSMENTPAGES_FAILURE:
            return { type: type, payload: payload }
        default:
            return state;
    }
};
export default reducer;