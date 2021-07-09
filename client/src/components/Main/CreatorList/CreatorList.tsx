import CreatorCard from '../CreatorCard/CreatorCard';
import { List } from './CreatorList.styles';

const CreatorList = () => {
  return (
    <List>
      <li>
        <CreatorCard />
      </li>
      <li>
        <CreatorCard />
      </li>
      <li>
        <CreatorCard />
      </li>
      <li>
        <CreatorCard />
      </li>
    </List>
  );
};

export default CreatorList;
