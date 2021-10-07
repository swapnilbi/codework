import { NgxMonacoEditorConfig } from 'ngx-monaco-editor';
//import './ngx-monaco-editor-loader';

declare let monaco: any;
export function myMonacoLoad() {
  monaco.languages.register({ id: 'java' });  
  monaco.languages.setMonarchTokensProvider('java', {           
        // Set defaultToken to invalid to see what you do not tokenize yet
        // defaultToken: 'invalid',      
        keywords: [
          'abstract', 'continue', 'for', 'new', 'switch', 'assert', 'goto', 'do',
          'if', 'private', 'this', 'break', 'protected', 'throw', 'else', 'public',
          'enum', 'return', 'catch', 'try', 'interface', 'static', 'class',
          'finally', 'const', 'super', 'while', 'true', 'false'
        ],
      
        typeKeywords: [
          'boolean', 'double', 'byte', 'int', 'short', 'char', 'void', 'long', 'float'
        ],
      
        operators: [
          '=', '>', '<', '!', '~', '?', ':', '==', '<=', '>=', '!=',
          '&&', '||', '++', '--', '+', '-', '*', '/', '&', '|', '^', '%',
          '<<', '>>', '>>>', '+=', '-=', '*=', '/=', '&=', '|=', '^=',
          '%=', '<<=', '>>=', '>>>='
        ],
      
        // we include these common regular expressions
        symbols:  /[=><!~?:&|+\-*\/\^%]+/,
      
        // C# style strings
        escapes: /\\(?:[abfnrtv\\"']|x[0-9A-Fa-f]{1,4}|u[0-9A-Fa-f]{4}|U[0-9A-Fa-f]{8})/,
      
        // The main tokenizer for our languages
        tokenizer: {
          root: [
            // identifiers and keywords
            [/[a-z_$][\w$]*/, { cases: { '@typeKeywords': 'keyword',
                                         '@keywords': 'keyword',
                                         '@default': 'identifier' } }],
            [/[A-Z][\w\$]*/, 'type.identifier' ],  // to show class names nicely
      
            // whitespace
            { include: '@whitespace' },
      
            // delimiters and operators
            [/[{}()\[\]]/, '@brackets'],
            [/[<>](?!@symbols)/, '@brackets'],
            [/@symbols/, { cases: { '@operators': 'operator',
                                    '@default'  : '' } } ],
      
            // @ annotations.
            // As an example, we emit a debugging log message on these tokens.
            // Note: message are supressed during the first load -- change some lines to see them.
            [/@\s*[a-zA-Z_\$][\w\$]*/, { token: 'annotation', log: 'annotation token: $0' }],
      
            // numbers
            [/\d*\.\d+([eE][\-+]?\d+)?/, 'number.float'],
            [/0[xX][0-9a-fA-F]+/, 'number.hex'],
            [/\d+/, 'number'],
      
            // delimiter: after number because of .\d floats
            [/[;,.]/, 'delimiter'],
      
            // strings
            [/"([^"\\]|\\.)*$/, 'string.invalid' ],  // non-teminated string
            [/"/,  { token: 'string.quote', bracket: '@open', next: '@string' } ],
      
            // characters
            [/'[^\\']'/, 'string'],
            [/(')(@escapes)(')/, ['string','string.escape','string']],
            [/'/, 'string.invalid']
          ],
      
          comment: [
            [/[^\/*]+/, 'comment' ],
            [/\/\*/,    'comment', '@push' ],    // nested comment
            ["\\*/",    'comment', '@pop'  ],
            [/[\/*]/,   'comment' ]
          ],
      
          string: [
            [/[^\\"]+/,  'string'],
            [/@escapes/, 'string.escape'],
            [/\\./,      'string.escape.invalid'],
            [/"/,        { token: 'string.quote', bracket: '@close', next: '@pop' } ]
          ],
      
          whitespace: [
            [/[ \t\r\n]+/, 'white'],
            [/\/\*/,       'comment', '@comment' ],
            [/\/\/.*$/,    'comment'],
          ],
        }          
  });
  
  monaco.languages.register({ id: 'python' });  
  monaco.languages.setMonarchTokensProvider('python', {
    defaultToken: '',
    tokenPostfix: '.python',

    keywords: [
      'and',
      'as',
      'assert',
      'break',
      'class',
      'continue',
      'def',
      'del',
      'elif',
      'else',
      'except',
      'exec',
      'finally',
      'for',
      'from',
      'global',
      'if',
      'import',
      'in',
      'is',
      'lambda',
      'None',
      'not',
      'or',
      'pass',
      'print',
      'raise',
      'return',
      'self',
      'try',
      'while',
      'with',
      'yield',

      'int',
      'float',
      'long',
      'complex',
      'hex',

      'abs',
      'all',
      'any',
      'apply',
      'basestring',
      'bin',
      'bool',
      'buffer',
      'bytearray',
      'callable',
      'chr',
      'classmethod',
      'cmp',
      'coerce',
      'compile',
      'complex',
      'delattr',
      'dict',
      'dir',
      'divmod',
      'enumerate',
      'eval',
      'execfile',
      'file',
      'filter',
      'format',
      'frozenset',
      'getattr',
      'globals',
      'hasattr',
      'hash',
      'help',
      'id',
      'input',
      'intern',
      'isinstance',
      'issubclass',
      'iter',
      'len',
      'locals',
      'list',
      'map',
      'max',
      'memoryview',
      'min',
      'next',
      'object',
      'oct',
      'open',
      'ord',
      'pow',
      'print',
      'property',
      'reversed',
      'range',
      'raw_input',
      'reduce',
      'reload',
      'repr',
      'reversed',
      'round',
      'set',
      'setattr',
      'slice',
      'sorted',
      'staticmethod',
      'str',
      'sum',
      'super',
      'tuple',
      'type',
      'unichr',
      'unicode',
      'vars',
      'xrange',
      'zip',

      'True',
      'False',

      '__dict__',
      '__methods__',
      '__members__',
      '__class__',
      '__bases__',
      '__name__',
      '__mro__',
      '__subclasses__',
      '__init__',
      '__import__'
    ],

    brackets: [
      { open: '{', close: '}', token: 'delimiter.curly' },
      { open: '[', close: ']', token: 'delimiter.bracket' },
      { open: '(', close: ')', token: 'delimiter.parenthesis' }
    ],

    tokenizer: {
      root: [
        { include: '@whitespace' },
        { include: '@numbers' },
        { include: '@strings' },

        [/[,:;]/, 'delimiter'],
        [/[{}\[\]()]/, '@brackets'],

        [/@[a-zA-Z]\w*/, 'tag'],
        [/[a-zA-Z]\w*/, {
          cases: {
            '@keywords': 'keyword',
            '@default': 'identifier'
          }
        }]
      ],

      // Deal with white space, including single and multi-line comments
      whitespace: [
        [/\s+/, 'white'],
        [/(^#.*$)/, 'comment'],
        [/('''.*''')|(""".*""")/, 'string'],
        [/'''.*$/, 'string', '@endDocString'],
        [/""".*$/, 'string', '@endDblDocString']
      ],
      endDocString: [
        [/\\'/, 'string'],
        [/.*'''/, 'string', '@popall'],
        [/.*$/, 'string']
      ],
      endDblDocString: [
        [/\\"/, 'string'],
        [/.*"""/, 'string', '@popall'],
        [/.*$/, 'string']
      ],

      // Recognize hex, negatives, decimals, imaginaries, longs, and scientific notation
      numbers: [
        [/-?0x([abcdef]|[ABCDEF]|\d)+[lL]?/, 'number.hex'],
        [/-?(\d*\.)?\d+([eE][+\-]?\d+)?[jJ]?[lL]?/, 'number']
      ],

      // Recognize strings, including those broken across lines with \ (but not without)
      strings: [
        [/'$/, 'string.escape', '@popall'],
        [/'/, 'string.escape', '@stringBody'],
        [/"$/, 'string.escape', '@popall'],
        [/"/, 'string.escape', '@dblStringBody']
      ],
      stringBody: [
        [/[^\\']+$/, 'string', '@popall'],
        [/[^\\']+/, 'string'],
        [/\\./, 'string'],
        [/'/, 'string.escape', '@popall'],
        [/\\$/, 'string']
      ],
      dblStringBody: [
        [/[^\\"]+$/, 'string', '@popall'],
        [/[^\\"]+/, 'string'],
        [/\\./, 'string'],
        [/"/, 'string.escape', '@popall'],
        [/\\$/, 'string']
      ]
    }
  });

  monaco.languages.register({ id: 'c' });  
  monaco.languages.setMonarchTokensProvider('c', {
    defaultToken: '',
    tokenPostfix: '.cs',
  
    brackets: [
      { open: '{', close: '}', token: 'delimiter.curly' },
      { open: '[', close: ']', token: 'delimiter.square' },
      { open: '(', close: ')', token: 'delimiter.parenthesis' },
      { open: '<', close: '>', token: 'delimiter.angle' }
    ],
  
    keywords: [
      'extern', 'alias', 'using', 'bool', 'decimal', 'sbyte', 'byte', 'short',
      'ushort', 'int', 'uint', 'long', 'ulong', 'char', 'float', 'double',
      'object', 'dynamic', 'string', 'assembly', 'is', 'as', 'ref',
      'out', 'this', 'base', 'new', 'typeof', 'void', 'checked', 'unchecked',
      'default', 'delegate', 'var', 'const', 'if', 'else', 'switch', 'case',
      'while', 'do', 'for', 'foreach', 'in', 'break', 'continue', 'goto',
      'return', 'throw', 'try', 'catch', 'finally', 'lock', 'yield', 'from',
      'let', 'where', 'join', 'on', 'equals', 'into', 'orderby', 'ascending',
      'descending', 'select', 'group', 'by', 'namespace', 'partial', 'class',
      'field', 'event', 'method', 'param', 'property', 'public', 'protected',
      'internal', 'private', 'abstract', 'sealed', 'static', 'struct', 'readonly',
      'volatile', 'virtual', 'override', 'params', 'get', 'set', 'add', 'remove',
      'operator', 'true', 'false', 'implicit', 'explicit', 'interface', 'enum',
      'null', 'async', 'await', 'fixed', 'sizeof', 'stackalloc', 'unsafe', 'nameof',
      'when'
    ],
  
    namespaceFollows: [
      'namespace', 'using',
    ],
  
    parenFollows: [
      'if', 'for', 'while', 'switch', 'foreach', 'using', 'catch', 'when'
    ],
  
    operators: [
      '=', '??', '||', '&&', '|', '^', '&', '==', '!=', '<=', '>=', '<<',
      '+', '-', '*', '/', '%', '!', '~', '++', '--', '+=',
      '-=', '*=', '/=', '%=', '&=', '|=', '^=', '<<=', '>>=', '>>', '=>'
    ],
  
    symbols: /[=><!~?:&|+\-*\/\^%]+/,
  
    // escape sequences
    escapes: /\\(?:[abfnrtv\\"']|x[0-9A-Fa-f]{1,4}|u[0-9A-Fa-f]{4}|U[0-9A-Fa-f]{8})/,
  
    // The main tokenizer for our languages
    tokenizer: {
      root: [
  
        // identifiers and keywords
        [/\@?[a-zA-Z_]\w*/, {
          cases: {
            '@namespaceFollows': { token: 'keyword.$0', next: '@namespace' },
            '@keywords': { token: 'keyword.$0', next: '@qualified' },
            '@default': { token: 'identifier', next: '@qualified' }
          }
        }],
  
        // whitespace
        { include: '@whitespace' },
  
        // delimiters and operators
        [/}/, {
          cases: {
            '$S2==interpolatedstring': { token: 'string.quote', next: '@pop' },
            '$S2==litinterpstring': { token: 'string.quote', next: '@pop' },
            '@default': '@brackets'
          }
        }],
        [/[{}()\[\]]/, '@brackets'],
        [/[<>](?!@symbols)/, '@brackets'],
        [/@symbols/, {
          cases: {
            '@operators': 'delimiter',
            '@default': ''
          }
        }],
  
  
        // numbers
        [/[0-9_]*\.[0-9_]+([eE][\-+]?\d+)?[fFdD]?/, 'number.float'],
        [/0[xX][0-9a-fA-F_]+/, 'number.hex'],
        [/0[bB][01_]+/, 'number.hex'], // binary: use same theme style as hex
        [/[0-9_]+/, 'number'],
  
        // delimiter: after number because of .\d floats
        [/[;,.]/, 'delimiter'],
  
        // strings
        [/"([^"\\]|\\.)*$/, 'string.invalid'],  // non-teminated string
        [/"/, { token: 'string.quote', next: '@string' }],
        [/\$\@"/, { token: 'string.quote', next: '@litinterpstring' }],
        [/\@"/, { token: 'string.quote', next: '@litstring' }],
        [/\$"/, { token: 'string.quote', next: '@interpolatedstring' }],
  
        // characters
        [/'[^\\']'/, 'string'],
        [/(')(@escapes)(')/, ['string', 'string.escape', 'string']],
        [/'/, 'string.invalid']
      ],
  
      qualified: [
        [/[a-zA-Z_][\w]*/, {
          cases: {
            '@keywords': { token: 'keyword.$0' },
            '@default': 'identifier'
          }
        }],
        [/\./, 'delimiter'],
        ['', '', '@pop'],
      ],
  
      namespace: [
        { include: '@whitespace' },
        [/[A-Z]\w*/, 'namespace'],
        [/[\.=]/, 'delimiter'],
        ['', '', '@pop'],
      ],
  
      comment: [
        [/[^\/*]+/, 'comment'],
        // [/\/\*/,    'comment', '@push' ],    // no nested comments :-(
        ['\\*/', 'comment', '@pop'],
        [/[\/*]/, 'comment']
      ],
  
      string: [
        [/[^\\"]+/, 'string'],
        [/@escapes/, 'string.escape'],
        [/\\./, 'string.escape.invalid'],
        [/"/, { token: 'string.quote', next: '@pop' }]
      ],
  
      litstring: [
        [/[^"]+/, 'string'],
        [/""/, 'string.escape'],
        [/"/, { token: 'string.quote', next: '@pop' }]
      ],
  
      litinterpstring: [
        [/[^"{]+/, 'string'],
        [/""/, 'string.escape'],
        [/{{/, 'string.escape'],
        [/}}/, 'string.escape'],
        [/{/, { token: 'string.quote', next: 'root.litinterpstring' }],
        [/"/, { token: 'string.quote', next: '@pop' }]
      ],
  
      interpolatedstring: [
        [/[^\\"{]+/, 'string'],
        [/@escapes/, 'string.escape'],
        [/\\./, 'string.escape.invalid'],
        [/{{/, 'string.escape'],
        [/}}/, 'string.escape'],
        [/{/, { token: 'string.quote', next: 'root.interpolatedstring' }],
        [/"/, { token: 'string.quote', next: '@pop' }]
      ],
  
      whitespace: [
        [/^[ \t\v\f]*#((r)|(load))(?=\s)/, 'directive.csx'],
        [/^[ \t\v\f]*#\w.*$/, 'namespace.cpp'],
        [/[ \t\v\f\r\n]+/, ''],
        [/\/\*/, 'comment', '@comment'],
        [/\/\/.*$/, 'comment'],
      ],
    }
  });

}
export const CodeEditorConfig: NgxMonacoEditorConfig = {
  baseUrl: '/assets',
  onMonacoLoad: myMonacoLoad
};
