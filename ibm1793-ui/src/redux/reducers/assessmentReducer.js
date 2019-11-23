import {
    FETCH_ASSESSMENT_SUCCESS,
    FETCH_ASSESSMENT_FAILURE
} from '../actions/assessmentAction';
import answerStatusObject from '../../states/assessments';
const defaultState = answerStatusObject.answerStatus;
function reducer(state = defaultState, { type, payload }) {
    switch (type) {
        case FETCH_ASSESSMENT_SUCCESS:
            switch (payload.question) {

                case '0':
                    answerStatusObject.answerStatus = {
                        question1: { answered: false, points: 0, answerSelected: "", id: "" },
                        question2: { answered: false, points: 0, answerSelected: "", id: "" },
                        question3: { answered: false, points: 0, answerSelected: "", id: "" },
                        question4: { answered: false, points: 0, answerSelected: "", id: "" },
                        question5: { answered: false, points: 0, answerSelected: "", id: "" },
                        question6: { answered: false, points: 0, answerSelected: "", id: "" },
                        question7: { answered: false, points: 0, answerSelected: "", id: "" },
                        question8: { answered: false, points: 0, answerSelected: "", id: "" },
                        question9: { answered: false, points: 0, answerSelected: "", id: "" }
                    }
                    answerStatusObject.answerStatus.totalScore = 0;
                    return payload = answerStatusObject.answerStatus;
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    answerStatusObject.answerStatus['question' + payload.question].answered = payload.answered;
                    answerStatusObject.answerStatus['question' + payload.question].points = payload.points;
                    answerStatusObject.answerStatus['question' + payload.question].answerSelected = payload.answerSelected;
                    answerStatusObject.answerStatus['question' + payload.question].id = payload.id;
                    return payload = answerStatusObject.answerStatus;
                default:
                    return defaultState;
            }
        case FETCH_ASSESSMENT_FAILURE:
            return { type: type, payload: payload }
        default:
            return state;
    }
};
export default reducer;