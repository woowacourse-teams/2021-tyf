import useCreatorList from '../../../service/hooks/useCreatorList';
import { StyledAnchor } from '../../@atom/Anchor/Anchor.styles';
import CreatorCard from '../CreatorCard/CreatorCard';
import { List } from './CreatorList.styles';

const CreatorList = () => {
  const { creatorList } = useCreatorList();

  return (
    <List>
      {creatorList.map((creator, index) => (
        <li key={index}>
          <StyledAnchor to={'/creator/' + creator.pageName}>
            <CreatorCard creator={creator} />
          </StyledAnchor>
        </li>
      ))}
    </List>
  );
};

export default CreatorList;
