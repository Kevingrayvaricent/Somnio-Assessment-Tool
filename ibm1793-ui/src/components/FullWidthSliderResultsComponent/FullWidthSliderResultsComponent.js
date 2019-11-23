import React, { Fragment } from 'react';

import { Download16 } from '@carbon/icons-react';
import { ArrowRight16 } from '@carbon/icons-react';

import documentImageSvg from '../../assets/images/document-image.svg';
import dotBackground from '../../assets/images/dot-background.png';
import card1 from '../../assets/images/card-1.svg';
import card2 from '../../assets/images/card-2.svg';
import card3 from '../../assets/images/card-3.svg';
import card4 from '../../assets/images/card-4.svg';
import card5 from '../../assets/images/card-5.svg';
import card6 from '../../assets/images/card-6.svg';

import './FullWidthSliderResultsComponent.css';

function FullWidthSliderResultsComponent() {
  return (
    <Fragment>
      <div>
        <div id="results" className="ibm-band ibm-padding-top-r1 results"
          style={{
            backgroundImage: "url(" + dotBackground + ")"
          }} >
          <div className="ibm-columns">
            <div className="ibm-col-6-3 ibm-col-medium-6-3 ibm-padding-top-1 ibm-padding-bottom-1">
              <img alt="" width="80" src={documentImageSvg} />
              <h2 className="ibm-h2 ibm-medium ibm-light"><span>Get your full report</span><span className="ibm-bold"></span></h2>
              <div className="ibm-columns">
                <div className="ibm-col-6-3 ibm-col-medium-1-1">
                  <p className="ibm-light">Congratulations! You've completed the SPM assessment. We've compiled a detailed report featuring customized recommendations guided by your responses. Download now for the next steps to optimizing SPM for your organization.</p>
                </div>
              </div>
              <p className="ibm-button-link ibm-alternate-background">
                <a href="/" className="ibm-btn-pri">Download full report<Download16 aria-label="Arrow right" className="arrow-rigth" /></a>
              </p>
            </div>
          </div>
        </div>
        <div className="ibm-columns resuls-grid ibm-padding-top-2 ibm-fluid ibm-flex ibm-flex--wrap">
          <div className="ibm-col-12-3 ibm-col-medium-12-12">
            <div className="ibm-columns">
              <div className="ibm-col-12-11 ibm-col-medium-12-12">
                <h2 className="ibm-h2 ibm-medium ibm-light">Trends in SPM</h2>
                <p className="learn-more">Learn more by exploring how modernizing SPM addresses specific challenges for businesses in every industry and every size.</p>
              </div>
            </div>
          </div>
          <div className="ibm-col-12-3 ibm-full-grid-text ibm-info ibm-col-medium-12-6">
            <p>Report</p>
            <img alt="" src={card1} />
            <h3>Gartner Magic Quadrant for Sales Performance Management (SPM)</h3>
            <a target="_blank" rel="noopener noreferrer" href="https://www.ibm.com/account/reg/us-en/signup?formid=urx-22202">See how IBM SPM measures up<ArrowRight16 aria-label="Arrow right" className="arrow-rigth" /></a>
          </div>
          <div className="ibm-col-12-3 ibm-info ibm-col-medium-12-6">
            <p>Report</p>
            <img alt="" src={card2} />
            <h3 className="forester-total">Forrester Total Economic Impact for IBM Incentive Compensation Management</h3>
            <a target="_blank" rel="noopener noreferrer" href="https://www.ibm.com/account/reg/ca-en/signup?formid=urx-15141">See the ROI from IBM SPM<ArrowRight16 aria-label="Arrow right" className="arrow-rigth" /></a>
          </div>
          <div className="ibm-col-12-3 ibm-info ibm-col-medium-12-6">
            <p>Experience</p>
            <img alt="" src={card3} />
            <h3>Speak to an SPM expert and discover what's right for your business</h3>
            <a target="_blank" rel="noopener noreferrer" href="https://www.ibm.com/account/reg/us-en/signup?formid=urx-35868">Leave behind legacy platforms<ArrowRight16 aria-label="Arrow right" className="arrow-rigth" /></a>
          </div>
          <div className="ibm-col-12-3 ibm-info hidden-info ibm-col-medium-12-6"></div>
          <div className="ibm-col-12-3 ibm-info ibm-col-medium-12-6">
            <p>Demonstration</p>
            <img alt="" src={card4} />
            <h3>Boost performance with Compensation Administrator</h3>
            <a target="_blank" rel="noopener noreferrer" href="https://embed.wirewax.com/8122735/">Optimize sales with IBM SPM<ArrowRight16 aria-label="Arrow right" className="arrow-rigth" /></a>
          </div>
          <div className="ibm-col-12-3 ibm-info ibm-col-medium-12-6">
            <p>Demonstration</p>
            <img alt="" src={card5} />
            <h3>Optimize your teams with IBM Sales Manager</h3>
            <a target="_blank" rel="noopener noreferrer" href="https://embed.wirewax.com/8123623/">Manage reps across the business<ArrowRight16 aria-label="Arrow right" className="arrow-rigth" /></a>
          </div>
          <div className="ibm-col-12-3 ibm-info ibm-col-medium-12-6">
            <p>Demonstration</p>
            <img alt="" src={card6} />
            <h3>Monitor your experience with IBM Sales Representative</h3>
            <a target="_blank" rel="noopener noreferrer" href="http://embed.wirewax.com/8124308/">Track performance on one dashboard<ArrowRight16 aria-label="Arrow right" className="arrow-rigth" /></a>
          </div>
        </div>

      </div>
    </Fragment>
  );
}

export default FullWidthSliderResultsComponent;
