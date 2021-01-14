/**
 * @license Copyright (c) 2014-2021, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */
// import ClassicEditor from '@ckeditor/ckeditor5-editor-classic/src/classiceditor.js';
// import Alignment from '@ckeditor/ckeditor5-alignment/src/alignment.js';
// import Autoformat from '@ckeditor/ckeditor5-autoformat/src/autoformat.js';
// import BlockQuote from '@ckeditor/ckeditor5-block-quote/src/blockquote.js';
import Bold from '@ckeditor/ckeditor5-basic-styles/src/bold.js';
// import CKFinder from '@ckeditor/ckeditor5-ckfinder/src/ckfinder.js';
// import Essentials from '@ckeditor/ckeditor5-essentials/src/essentials.js';
// import FontBackgroundColor from '@ckeditor/ckeditor5-font/src/fontbackgroundcolor.js';
// import FontColor from '@ckeditor/ckeditor5-font/src/fontcolor.js';
// import FontFamily from '@ckeditor/ckeditor5-font/src/fontfamily.js';
// import FontSize from '@ckeditor/ckeditor5-font/src/fontsize.js';
// import Heading from '@ckeditor/ckeditor5-heading/src/heading.js';
// import Image from '@ckeditor/ckeditor5-image/src/image.js';
// import ImageCaption from '@ckeditor/ckeditor5-image/src/imagecaption.js';
// import ImageStyle from '@ckeditor/ckeditor5-image/src/imagestyle.js';
// import ImageToolbar from '@ckeditor/ckeditor5-image/src/imagetoolbar.js';
// import ImageUpload from '@ckeditor/ckeditor5-image/src/imageupload.js';
// import Indent from '@ckeditor/ckeditor5-indent/src/indent.js';
import Italic from '@ckeditor/ckeditor5-basic-styles/src/italic.js';
import Link from '@ckeditor/ckeditor5-link/src/link.js';
import List from '@ckeditor/ckeditor5-list/src/list.js';
import MediaEmbed from '@ckeditor/ckeditor5-media-embed/src/mediaembed.js';
import PageBreak from '@ckeditor/ckeditor5-page-break/src/pagebreak.js';
import Paragraph from '@ckeditor/ckeditor5-paragraph/src/paragraph.js';
import PasteFromOffice from '@ckeditor/ckeditor5-paste-from-office/src/pastefromoffice';
import SpecialCharacters from '@ckeditor/ckeditor5-special-characters/src/specialcharacters.js';
import Strikethrough from '@ckeditor/ckeditor5-basic-styles/src/strikethrough.js';
import Table from '@ckeditor/ckeditor5-table/src/table.js';
import TableCellProperties from '@ckeditor/ckeditor5-table/src/tablecellproperties';
import TableProperties from '@ckeditor/ckeditor5-table/src/tableproperties';
import TableToolbar from '@ckeditor/ckeditor5-table/src/tabletoolbar.js';
import TextTransformation from '@ckeditor/ckeditor5-typing/src/texttransformation.js';
import TodoList from '@ckeditor/ckeditor5-list/src/todolist';
import Underline from '@ckeditor/ckeditor5-basic-styles/src/underline.js';


import ClassicEditor from '@ckeditor/ckeditor5-editor-classic/src/classiceditor';
import Bold from '@ckeditor/ckeditor5-basic-styles/src/bold.js';
import Italic from '@ckeditor/ckeditor5-basic-styles/src/italic.js';
import Underline from '@ckeditor/ckeditor5-basic-styles/src/underline.js';

// Plugins to include in the build.
Editor.builtinPlugins = [
	// Alignment,
	// Autoformat,
	// BlockQuote,
	Bold,
	// CKFinder,
	// CKFinderUploadAdapter,
	// Essentials,
	// FontBackgroundColor,
	// FontColor,
	// FontFamily,
	// FontSize,
	// Heading,
	// Image,
	// ImageCaption,
	// ImageStyle,
	// ImageToolbar,
	// ImageUpload,
	// Indent,
	Italic,
	Link,
	// List,
	// MediaEmbed,
	// PageBreak,
	// Paragraph,
	// PasteFromOffice,
	// SpecialCharacters,
	// Strikethrough,
	// Table,
	// TableCellProperties,
	// TableProperties,
	// TableToolbar,
	// TextTransformation,
	// TodoList,
	Underline
];

export default Editor;
