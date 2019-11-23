import React, { Component } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import Page from './FullWidthSliderComponentPage';
import getRemoveAndShowResultsComponents from '../../redux/actions/removeAndShowResultsAction';
import getRemoveAndShowComponents from '../../redux/actions/removeAndShowContentAction';

class FullWidthSliderComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
        this.removeResultsComponent = this.removeResultsComponent.bind(this);
        this.showContentComponentAssessment = this.showContentComponentAssessment.bind(this);
    };

    async componentDidMount() {
    };

    componentDidUpdate(prevProps, prevState) {
    };

    removeResultsComponent() {
        this.props.getRemoveAndShowResultsComponents(false);
        this.props.getRemoveAndShowComponents(false);
    }

    showContentComponentAssessment(event) {
        this.props.getRemoveAndShowComponents(true);
        event.preventDefault()
        setTimeout(function () {
            window.location.href = "#ibm-top";

        }, 50);
    }

    render() {

        const {
            showContentComponent,
            removeAndShowContent,
            removeAndShowResults
        } = this.props;

        return (
            <Page showContentComponent={showContentComponent}
                removeAndShowContent={removeAndShowContent}
                removeResultsComponent={this.removeResultsComponent}
                showContentComponentAssessment={this.showContentComponentAssessment}
                removeAndShowResults={removeAndShowResults} />
        );
    };
};

const mapDispatchToProps = {
    getRemoveAndShowResultsComponents,
    getRemoveAndShowComponents
};

const mapStateToProps = state => ({
    removeAndShowContent: state.removeAndShowContent,
    removeAndShowResults: state.removeAndShowResults
});



export default withRouter(
    connect(mapStateToProps, mapDispatchToProps)(FullWidthSliderComponent)
);

