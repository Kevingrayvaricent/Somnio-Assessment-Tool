import React from 'react';
import { ArrowRight16 } from '@carbon/icons-react';
import { PageFirst16 } from '@carbon/icons-react';

import headerJpg from '../../assets/images/header.png';


import './FullWidthSliderComponent.css';

function FullWidthSliderComponent(props) {
  const { showContentComponentAssessment, removeResultsComponent, removeAndShowContent, removeAndShowResults } = props;
  return (
    <div className="ibm-band ibm-padding-top-r1 ibm-padding-bottom-r1"
      style={{
        backgroundImage: "url(" + headerJpg + ")"
      }} >
      <div className="ibm-columns">
        <div className="ibm-col-6-4 ibm-col-medium-12-8 ibm-padding-top-2 ibm-padding-bottom-2">
          <h4 className="ibm-h4 ibm-medium ibm-light">
            <span>IBM Sales Performance Management Assessment</span>
          </h4>
          <h1 className="ibm-h1 ibm-padding-bottom-1">Improve territories,<br />quotas and compensation<br />with fast insights.</h1>
          <h1 className="ibm-h1 ibm-padding-bottom-1 only-mobile">Improve<br />territories, quotas<br />and compensation<br />with fast insights.</h1>
          {!removeAndShowResults ?
            <p className="ibm-light only-desktop">Complete the assessment and get tailored recommendations<br />for improving SPM across your business.</p>
            : null}
          {!removeAndShowResults ?
            <p className="ibm-light only-mobile">Complete the assessment<br />and get tailored<br />recommendations for<br />improving SPM across<br />your business.</p>
            : null}
          {removeAndShowResults ?
            <a onClick={removeResultsComponent} href className="ibm-start-again">Start again<PageFirst16 aria-label="Arrow right" className="arrow-rigth" /></a>
            : null}
          <p className="ibm-button-link ibm-alternate-background">
            {!removeAndShowContent ? !removeAndShowResults ?
              <a href onClick={showContentComponentAssessment} className="ibm-btn-pri">Start assessment
          <ArrowRight16 aria-label="Arrow right" className="arrow-rigth" /></a>
              : null : null}
          </p>
          {removeAndShowContent ? !removeAndShowResults ? <p className="ibm-light">All questions must be answered to receive your result.</p> : null : null}
        </div>
      </div>
    </div>
  );
}

export default FullWidthSliderComponent;
