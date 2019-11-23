import {
    FETCH_REMOVE_AND_SHOW_SUCCESS,
    FETCH_REMOVE_AND_SHOW_FAILURE
} from '../actions/removeAndShowContentAction';
const defaultState = false; //false
function reducer(state = defaultState, { type, payload }) {
    switch (type) {
        case FETCH_REMOVE_AND_SHOW_SUCCESS:
            return payload;
        case FETCH_REMOVE_AND_SHOW_FAILURE:
            return { type: type, payload: payload }
        default:
            return state;
    }
};
export default reducer;