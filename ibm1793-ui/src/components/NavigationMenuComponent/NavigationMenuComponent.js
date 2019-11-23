import React, { Component } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import Page from './NavigationMenuComponentPage';
import getRemoveAndShowComponents from '../../redux/actions/removeAndShowContentAction';
import getAssesmentResults from '../../redux/actions/assessmentAction';
import getRemoveAndShowResultsComponents from '../../redux/actions/removeAndShowResultsAction';
import getChangeAssessmentPages from '../../redux/actions/changeAssessmentPagesAction';
import answerStatusObject from '../../states/assessments';

class NavigationMenuComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showAndHideQuestions: true,
            allowLockResults: false
        };
        this.removeAssessmentComponent = this.removeAssessmentComponent.bind(this);
        this.showAssessmentInitialSection = this.showAssessmentInitialSection.bind(this);
        this.showAssessmentFinalSection = this.showAssessmentFinalSection.bind(this);
        this.getResults = this.getResults.bind(this);
    };

    async componentDidMount() {
    };

    componentDidUpdate(prevProps, prevState) {
    };

    removeAssessmentComponent() {
        this.props.getRemoveAndShowComponents(false);
        this.props.getAssesmentResults(false);
        this.props.getRemoveAndShowResultsComponents(false);
        this.props.getChangeAssessmentPages(true);
        this.setState({ allowLockResults: false });

        this.props.getAssesmentResults({
            question: '0'
        });
    }

    showAssessmentInitialSection() {
        let setUnlockState = true;
        this.props.getChangeAssessmentPages(true);
        this.props.getRemoveAndShowResultsComponents(false);
        for (var i = 1; i <= 9; i++) {
            if (!answerStatusObject.answerStatus['question' + i].answered) {
                setUnlockState = false;
            }
        }
        this.setState({ allowLockResults: setUnlockState });

    }

    showAssessmentFinalSection(event) {
        let isSecondPartAllowed = true;
        let setUnlockState = true;
        let lessValueRequired = 1;
        let allValuesArray = [];
        for (var i = 1; i <= 5; i++) {
            if (!answerStatusObject.answerStatus['question' + i].answered) {
                isSecondPartAllowed = false;
                allValuesArray.push(i);
                if (document.getElementById("question" + i)) {
                    document.getElementById("question" + i).classList.add("required");
                }
            }
        }
        for (var y = 1; y <= 9; y++) {
            if (!answerStatusObject.answerStatus['question' + y].answered) {
                setUnlockState = false;
            }
        }
        console.log('isSecondPartAllowed ' + isSecondPartAllowed);
        lessValueRequired = Math.min.apply(null, allValuesArray);
        if (isSecondPartAllowed) {
            this.props.getChangeAssessmentPages(false);
            this.props.getRemoveAndShowResultsComponents(false);
            setTimeout(function () {
                window.location.href = "#ibm-top";
                event.preventDefault()
            }, 50);
        }
        else {
            window.location.href = "#question_content_question_" + lessValueRequired;
            event.preventDefault()
        }
        this.setState({ allowLockResults: setUnlockState });
        console.log(this.state.allowLockResults);
    }

    getResults() {
        let isResultComponentAllowed = true;
        for (var i = 1; i <= 9; i++) {
            console.log(answerStatusObject.answerStatus['question' + i].answered);
            if (!answerStatusObject.answerStatus['question' + i].answered) {
                isResultComponentAllowed = false;
            }
        }
        if (isResultComponentAllowed) {
            this.props.getRemoveAndShowResultsComponents(true);

            setTimeout(function () {
                window.location.href = "#ibm-top";
            }, 50);

        }

    }

    render() {
        const {
            showAndHideQuestions,
            allowLockResults
        } = this.state;

        const {
            removeAndShowContent,
            removeAndShowResults
        } = this.props;

        return (
            <Page
                removeAndShowContent={removeAndShowContent}
                removeAndShowResults={removeAndShowResults}
                showContent={this.showContent}
                showAndHideQuestions={showAndHideQuestions}
                allowLockResults={allowLockResults}
                removeAssessmentComponent={this.removeAssessmentComponent}
                showAssessmentInitialSection={this.showAssessmentInitialSection}
                showAssessmentFinalSection={this.showAssessmentFinalSection}
                getResults={this.getResults} />
        );
    };
};

const mapDispatchToProps = {
    getRemoveAndShowComponents,
    getAssesmentResults,
    getRemoveAndShowResultsComponents,
    getChangeAssessmentPages
};

const mapStateToProps = state => ({
    removeAndShowContent: state.removeAndShowContent,
    removeAndShowResults: state.removeAndShowResults
});

export default withRouter(
    connect(mapStateToProps, mapDispatchToProps)(NavigationMenuComponent)
);

