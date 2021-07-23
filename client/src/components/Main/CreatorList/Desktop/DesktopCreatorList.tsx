import useCreatorList from '../../../../service/hooks/useCreatorList';
import Anchor from '../../../@atom/Anchor/Anchor';
import IconButton from '../../../@atom/IconButton/IconButton';
import CreatorCard from '../../CreatorCard/CreatorCard';
import { List } from './DesktopCreatorList.styles';
import LeftArrow from '../../../../assets/icons/left-arrow.svg';
import RightArrow from '../../../../assets/icons/right-arrow.svg';

const DesktopCreatorList = () => {
  const { creatorList } = useCreatorList();

  return (
    <List>
      <IconButton src={LeftArrow} />
      {creatorList.slice(0, 3).map((creator, index) => (
        <li key={index}>
          <Anchor to={'/creator/' + creator.pageName}>
            <CreatorCard creator={creator} />
          </Anchor>
        </li>
      ))}
      <IconButton src={RightArrow} />
    </List>
  );
};

export default DesktopCreatorList;
