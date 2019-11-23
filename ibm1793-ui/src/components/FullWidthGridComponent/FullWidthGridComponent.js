import React, { Component } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import Page from './FullWidthGridComponentPage';
import getRemoveAndShowComponents from '../../redux/actions/removeAndShowContentAction';



class FullWidthGridComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
        this.removeResultsComponent = this.removeResultsComponent.bind(this);
    };

    async componentDidMount() {
    };

    componentDidUpdate(prevProps, prevState) {
    };

    removeResultsComponent() {
        this.props.getRemoveAndShowComponents(true);
        setTimeout(function () {
            if (navigator.userAgent.match(/iPad/i)) {
                window.location.href = "#ibm-top";
            } else {
                window.location.href = "#ibm-top";
            }
        }, 50);
    }

    render() {

        return (
            <Page removeResultsComponent={this.removeResultsComponent} />
        );
    };
};

const mapDispatchToProps = {
    getRemoveAndShowComponents
};

const mapStateToProps = state => ({
});


export default withRouter(
    connect(mapStateToProps, mapDispatchToProps)(FullWidthGridComponent)
);
