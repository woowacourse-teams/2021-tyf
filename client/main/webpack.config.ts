import * as webpack from 'webpack';
import webpackDevServer from 'webpack-dev-server';
import path from 'path';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import ForkTsCheckerWebpackPlugin from 'fork-ts-checker-webpack-plugin';
import { BundleAnalyzerPlugin } from 'webpack-bundle-analyzer';
import ImageMinimizerPlugin from 'image-minimizer-webpack-plugin';
import CompressionPlugin from 'compression-webpack-plugin';

interface WebpackConfig extends webpack.Configuration {
  devServer?: webpackDevServer.Configuration;
}

const isProduction = process.env.NODE_ENV === 'production';

const config: WebpackConfig = {
  entry: path.resolve('src', 'index.tsx'),
  resolve: { extensions: ['.ts', '.tsx', '.js', '.jsx'] },
  output: {
    filename: '[contenthash].bundle.js',
    path: path.resolve('server', 'dist'),
    assetModuleFilename: 'static/[hash][ext][query]',
    clean: true,
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        exclude: /node_modules/,
        use: [
          {
            loader: 'ts-loader',
            options: {
              transpileOnly: true,
            },
          },
        ],
      },
      {
        test: /\.(svg|gif|webp)$/i,
        type: 'asset/resource',
      },
      {
        test: /\.(png|jpg)$/i,
        type: 'asset/resource',
        generator: {
          filename: 'static/[hash].webp',
        },
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve('public', 'index.html'),
      base: '/',
      favicon: path.resolve('public', 'favicon.ico'),
    }),
    new ForkTsCheckerWebpackPlugin({
      typescript: {
        diagnosticOptions: {
          semantic: true,
          syntactic: true,
        },
      },
    }),
    new ImageMinimizerPlugin({
      minimizerOptions: {
        plugins: [['webp'], ['svgo']],
      },
    }),
    new CompressionPlugin({
      test: /\.(js|html)$/,
      filename: 'compressed/[path][base].gz',
    }),
    new BundleAnalyzerPlugin({
      analyzerMode: 'disabled',
      generateStatsFile: true,
      statsFilename: 'docs/bundle_stats.json',
      // reportFilename: 'docs/bundle_size.html', // output: html
    }) as unknown as webpack.WebpackPluginInstance,
  ],
  devServer: {
    port: 9000,
    historyApiFallback: true,
    open: true,
  },
  optimization: {
    runtimeChunk: true,
    splitChunks: {
      chunks: 'all',
    },
  },
};

config.mode = isProduction ? 'production' : 'development';

export default config;
