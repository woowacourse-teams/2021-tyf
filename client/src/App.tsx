interface Props {
  hi: string;
}

const App = ({ hi }: Props) => {
  return <h1>{hi}</h1>;
};

export default App;
