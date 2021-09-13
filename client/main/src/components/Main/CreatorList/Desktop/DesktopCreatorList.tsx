import useCreatorList from '../../../../service//creator/useCreatorList';
import Anchor from '../../../@atom/Anchor/Anchor';
import CreatorCard from '../../CreatorCard/CreatorCard';
import {
  ItemContainer,
  List,
  RightArrowButton,
  LeftArrowButton,
} from './DesktopCreatorList.styles';
import LeftArrow from '../../../../assets/icons/left-arrow.svg';
import RightArrow from '../../../../assets/icons/right-arrow.svg';

const DesktopCreatorList = () => {
  const { listRef, creatorList, showPrevList, showNextList, isFirstPage, isLastPage } =
    useCreatorList();

  // TODO: Carousel 컴포넌트로 대체 및 Lists do not contain only <li> elements and script supporting elements 해결

  return (
    <List>
      {!isFirstPage && (
        <LeftArrowButton aria-label="left-arrow" src={LeftArrow} onClick={showPrevList} />
      )}
      <ItemContainer ref={listRef}>
        {creatorList.map((creator, index) => (
          <li key={index}>
            <Anchor to={'/creator/' + creator.pageName}>
              <CreatorCard creator={creator} />
            </Anchor>
          </li>
        ))}
      </ItemContainer>
      {!isLastPage && (
        <RightArrowButton aria-label="right-arrow" src={RightArrow} onClick={showNextList} />
      )}
    </List>
  );
};

export default DesktopCreatorList;
