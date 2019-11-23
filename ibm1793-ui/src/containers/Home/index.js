import React, { Component } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import Page from './HomePage';
import getRemoveAndShowComponents from '../../redux/actions/removeAndShowContentAction';


class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
    };
    this.showContentComponent = this.showContentComponent.bind(this);
  };

  async componentDidMount() {
  };

  componentDidUpdate(prevProps, prevState) {
  };

  showContentComponent() {
    this.props.getRemoveAndShowComponents(true);
  }

  render() {

    const {
      removeAndShowContent,
      removeAndShowResults
    } = this.props;

    return (
      <Page
        removeAndShowContent={removeAndShowContent}
        removeAndShowResults={removeAndShowResults}
        showContentComponent={this.showContentComponent} />
    );
  };
};

const mapStateToProps = state => ({
  removeAndShowContent: state.removeAndShowContent,
  removeAndShowResults: state.removeAndShowResults
});

const mapDispatchToProps = {
  getRemoveAndShowComponents
};

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(Home)
);