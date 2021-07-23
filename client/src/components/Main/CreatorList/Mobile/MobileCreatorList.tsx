import useCreatorList from '../../../../service/hooks/useCreatorList';
import Anchor from '../../../@atom/Anchor/Anchor';
import CreatorCard from '../../CreatorCard/CreatorCard';
import { List } from './MobileCreatorList.styles';

const MobileCreatorList = () => {
  const { creatorList } = useCreatorList();

  return (
    <List>
      {creatorList.map((creator, index) => (
        <li key={index}>
          <Anchor to={'/creator/' + creator.pageName}>
            <CreatorCard creator={creator} />
          </Anchor>
        </li>
      ))}
    </List>
  );
};

export default MobileCreatorList;
