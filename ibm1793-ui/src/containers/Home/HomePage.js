import React, { Fragment } from 'react';
import './Home.scss';
import StickyHeader from '../../components/StickyHeaderIBMComponent/StickyHeader';
import NavigationMenuComponent from '../../components/NavigationMenuComponent/NavigationMenuComponent'
import FooterComponent from '../../components/FooterIBMComponent/FooterComponent';
import FullWidthGridComponent from '../../components/FullWidthGridComponent/FullWidthGridComponent';
import FullWidthSliderComponent from '../../components/FullWidthSliderComponent/FullWidthSliderComponent';
import AssessmentComponent from '../../components/AssessmentComponent/AssessmentComponent';
import FullWidthSliderResultsComponent from '../../components/FullWidthSliderResultsComponent/FullWidthSliderResultsComponent';


function Page(props) {
	const { removeAndShowContent, showContentComponent, removeAndShowResults, changeAssessmentPages } = props;
	return (
		<Fragment>
			<StickyHeader />
			<div id="ibm-content-wrapper">
				<header role="banner" aria-labelledby="ibm-pagetitle-h1">
					<NavigationMenuComponent changeAssessmentPages={changeAssessmentPages} showContentComponent={showContentComponent} removeAndShowContent={removeAndShowContent} />
					<FullWidthSliderComponent showContentComponent={showContentComponent} removeAndShowContent={removeAndShowContent} />
				</header>
			</div>
			{!removeAndShowContent ? <FullWidthGridComponent showContentComponent={showContentComponent} /> : null}
			{removeAndShowContent ? <AssessmentComponent removeAndShowContent={removeAndShowContent} /> : null}
			{removeAndShowResults ? <FullWidthSliderResultsComponent /> : null}
			<FooterComponent />
		</Fragment >
	);
}

export default Page;