import useCreatorList from '../../../../service/hooks/creator/useCreatorList';
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

  return (
    <List>
      {!isFirstPage && <LeftArrowButton src={LeftArrow} onClick={showPrevList} />}
      <ItemContainer ref={listRef}>
        {creatorList.map((creator, index) => (
          <li key={index}>
            <Anchor to={'/creator/' + creator.pageName}>
              <CreatorCard creator={creator} />
            </Anchor>
          </li>
        ))}
      </ItemContainer>
      {!isLastPage && <RightArrowButton src={RightArrow} onClick={showNextList} />}
    </List>
  );
};

export default DesktopCreatorList;
